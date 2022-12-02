package me.taison.wizardpractice.data.factory;


import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.addons.IAddonController;

import java.util.ArrayList;
import java.util.List;

public class AddonFactory {

    private List<IAddonController> addonControllers;
    private final WizardPractice wizardPractice;

    public AddonFactory(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;

        this.addonControllers = new ArrayList<>();

        this.initializeAddons();
    }

    public void initializeAddons(){
        this.addonControllers.forEach(IAddonController::initialize);
    }

    public void deinitializeAddons(){
        this.addonControllers.forEach(IAddonController::deinitialize);
    }

}
