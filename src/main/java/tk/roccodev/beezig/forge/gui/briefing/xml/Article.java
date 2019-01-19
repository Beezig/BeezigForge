package tk.roccodev.beezig.forge.gui.briefing.xml;

import java.util.Date;

public class Article {

    private String author, title;
    private Date pubDate;
    private String content, link;

    private int x, y, width, height;
    private String clickHere;
    private boolean shown;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public String getChatComponent() {
        return clickHere;
    }

    public void setLink(String link) {
        this.link = link;
        this.clickHere = "§7[Read More]";
    }
}
