package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.impl.PracticeUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PracticeUserFactory {

    private Map<UUID, PracticeUser> practiceUserMap;

    private final WizardPractice wizardPractice;

    public PracticeUserFactory(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;

        this.practiceUserMap = new HashMap<>();
    }

    public void loadBoxUsers(){
        //TODO boxPVP.getUserDatabase.load();
    }

    public void saveBoxUsers(){
        //TODO boxPVP.getUserDatabase.save();
    }

    public void registerUser(PracticeUser practiceUser){
        this.practiceUserMap.putIfAbsent(practiceUser.getUniqueIdentifier(), practiceUser);
    }

    public void unregisterUser(PracticeUser boxUser){
        this.practiceUserMap.remove(boxUser.getUniqueIdentifier());
    }

    public Optional<PracticeUser> getUserByUniqueIdentifier(UUID uniqueIdentifier){
        return Optional.ofNullable(this.practiceUserMap.get(uniqueIdentifier));
    }

    public Optional<PracticeUser> getUserByName(String name){
        for(PracticeUser boxUser : this.practiceUserMap.values()){
            if(boxUser.getName().equals(name)){
                return Optional.of(boxUser);
            }
        }
        return Optional.empty();
    }

}
