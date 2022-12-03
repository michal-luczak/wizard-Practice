package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.factory.UserFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserFactoryImpl implements UserFactory {

    private Map<UUID, User> practiceUserMap;

    private final WizardPractice wizardPractice;

    public UserFactoryImpl(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;

        this.practiceUserMap = new HashMap<>();
    }

    @Override
    public void loadBoxUsers(){
        //TODO boxPVP.getUserDatabase.load();
    }

    @Override
    public void saveBoxUsers(){
        //TODO boxPVP.getUserDatabase.save();
    }

    @Override
    public void registerUser(User user){
        this.practiceUserMap.putIfAbsent(user.getUniqueIdentifier(), user);
    }
    @Override
    public void unregisterUser(User boxUser){
        this.practiceUserMap.remove(boxUser.getUniqueIdentifier());
    }

    @Override
    public Optional<User> getUserByUniqueIdentifier(UUID uniqueIdentifier){
        return Optional.ofNullable(this.practiceUserMap.get(uniqueIdentifier));
    }

    @Override
    public Optional<User> getUserByName(String name){
        for(User boxUser : this.practiceUserMap.values()){
            if(boxUser.getName().equals(name)){
                return Optional.of(boxUser);
            }
        }
        return Optional.empty();
    }

}