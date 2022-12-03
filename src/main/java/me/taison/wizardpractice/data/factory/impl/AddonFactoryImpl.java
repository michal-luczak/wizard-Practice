package me.taison.wizardpractice.data.factory.impl;


import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.addons.AddonController;
import me.taison.wizardpractice.data.factory.AddonFactory;

import java.util.ArrayList;
import java.util.List;

public class AddonFactoryImpl implements AddonFactory {

    private List<AddonController> addonControllers;
    private final WizardPractice wizardPractice;

    public AddonFactoryImpl(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;

        this.addonControllers = new ArrayList<>();

        this.initializeAddons();
    }

    @Override
    public void initializeAddons(){
        this.addonControllers.forEach(AddonController::initialize);
    }

    @Override
    public void deinitializeAddons(){
        this.addonControllers.forEach(AddonController::deinitialize);
    }

}
