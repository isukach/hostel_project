package war.webapp.service.builder;

public abstract class BaseDutyListTemplateBuilder {
    public void createTemplate(Object... params) {
        createHeader(params);
        createContent(params);
        createFooter(params);
    }

    public abstract void createHeader(Object... params);

    public abstract void createContent(Object... params);

    public abstract void createFooter(Object... params);

    public abstract byte[] build(Object... params);
}
