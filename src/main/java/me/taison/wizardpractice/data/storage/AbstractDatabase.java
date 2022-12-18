package me.taison.wizardpractice.data.storage;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.storage.util.Database;
import org.apache.commons.lang.Validate;

public abstract class AbstractDatabase implements Database {

    private final WizardPractice wizardPractice;

    public AbstractDatabase(WizardPractice wizardPractice) {
        Validate.notNull(wizardPractice);

        this.wizardPractice = wizardPractice;
    }

    @Override
    public void open() {
        this.create();
    }

    @Override
    public void close() {
        this.destroy();
    }

    protected abstract void create();

    protected abstract void destroy();

}
