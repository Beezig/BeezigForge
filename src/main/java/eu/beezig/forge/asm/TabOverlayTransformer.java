/*
 * Copyright (C) 2017-2020 Beezig Team
 *
 * This file is part of Beezig.
 *
 * Beezig is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beezig is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Beezig.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.forge.asm;

import com.mojang.authlib.GameProfile;
import eu.beezig.forge.badge.BadgeRenderer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;
import java.util.UUID;

@SuppressWarnings("unused")
public class TabOverlayTransformer implements IClassTransformer {

    private enum Mapping {
        CLASS_NAME("GuiPlayerTabOverlay","awh"),
        METHOD_NAME("renderPlayerlist","a"),
        METHOD_DESC("(ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V", "(ILauo;Lauk;)V"),
        NETWORK_PLAYER_INFO_DESC("net/minecraft/client/network/NetworkPlayerInfo", "bdc"),
        GAME_PROFILE_METHOD_NAME("getGameProfile", "a"),
        GAME_PROFILE_METHOD_DESC("()Lcom/mojang/authlib/GameProfile;", "()Lcom/mojang/authlib/GameProfile;");

        String plainName;
        String obfName;

        Mapping (String plainName, String obfName){
            this.plainName = plainName;
            this.obfName = obfName;
        }

        String get(boolean obfuscated) {
            return obfuscated ? obfName : plainName;
        }

        // shorthand for string comparison
        boolean equalsString(String in, boolean obfuscated) {
            return in.equals(get(obfuscated));
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals(Mapping.CLASS_NAME.plainName)) {
            return transform(basicClass, false);
        } else if (name.equals(Mapping.CLASS_NAME.obfName)) {
            return transform(basicClass, true);
        }
        return basicClass;
    }

    private byte[] transform(byte[] basicClass, boolean obf) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        try {
            for (MethodNode m : classNode.methods) {
                if (Mapping.METHOD_NAME.equalsString(m.name, obf) && Mapping.METHOD_DESC.equalsString(m.desc, obf)) {
                    for (ListIterator<AbstractInsnNode> it = m.instructions.iterator(); it.hasNext(); ) {
                        AbstractInsnNode n = it.next();
                        // Find our entry point
                        // Add necessary space
                        if (n.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                                ((MethodInsnNode) n).owner.equals(Mapping.CLASS_NAME.get(obf))) {
                            // Skip two nodes
                            it.next();
                            n = it.next();
                            if (n.getOpcode() == Opcodes.ISTORE && ((VarInsnNode) n).var == 10)
                            {
                                // Find the next label
                                AbstractInsnNode label = n;
                                while (!(label instanceof LabelNode)) {
                                    label = label.getNext();
                                }
                                InsnList list = new InsnList();
                                list.add(new VarInsnNode(Opcodes.ALOAD, 9));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                        Mapping.NETWORK_PLAYER_INFO_DESC.get(obf),
                                        Mapping.GAME_PROFILE_METHOD_NAME.get(obf),
                                        Mapping.GAME_PROFILE_METHOD_DESC.get(obf), false));
                                list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                        Type.getInternalName(GameProfile.class),
                                        "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId")),
                                        false));
                                list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(BadgeRenderer.class),
                                        "shouldRenderTabBadge",
                                        Type.getMethodDescriptor(BadgeRenderer.class.getMethod("shouldRenderTabBadge",
                                                UUID.class)),
                                        false));
                                list.add(new JumpInsnNode(Opcodes.IFEQ, (LabelNode) label));
                                list.add(new IincInsnNode(10, 10));
                                m.instructions.insert(n, list);
                            }
                        }
                        // Render the badge
                        if (n.getOpcode() == Opcodes.ILOAD && ((VarInsnNode) n).var == 22 && (n = it.next()) != null
                            && n.getOpcode() == Opcodes.I2F && (n = it.next()) != null) {
                            InsnList list = new InsnList();
                            list.add(new VarInsnNode(Opcodes.ALOAD, 24));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, Mapping.NETWORK_PLAYER_INFO_DESC.get(obf),
                                    Mapping.GAME_PROFILE_METHOD_NAME.get(obf), Mapping.GAME_PROFILE_METHOD_DESC.get(obf), false));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                                    Type.getInternalName(GameProfile.class),
                                    "getId", Type.getMethodDescriptor(GameProfile.class.getMethod("getId")),
                                    false));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 22));
                            list.add(new InsnNode(Opcodes.I2F));
                            list.add(new InsnNode(Opcodes.F2D));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 23));
                            list.add(new InsnNode(Opcodes.I2F));
                            list.add(new InsnNode(Opcodes.F2D));
                            list.add(new LdcInsnNode(8.0D));
                            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(BadgeRenderer.class),
                                    "renderBadge",
                                    Type.getMethodDescriptor(BadgeRenderer.class.getMethod("renderBadge",
                                            UUID.class, double.class, double.class, double.class)), false));
                            list.add(new InsnNode(Opcodes.IADD));
                            list.add(new InsnNode(Opcodes.I2F));
                            m.instructions.remove(n.getPrevious());
                            m.instructions.insertBefore(n, list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
