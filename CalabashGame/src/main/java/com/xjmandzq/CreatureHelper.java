
package com.xjmandzq;

import java.lang.annotation.*;
import com.xjmandzq.Creature.Camp;

// @Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
@interface  CreatureHelper{

    Camp camp();
    String cname();
    boolean healer();
    int speed();
    boolean alive();
    String rscname();
    int maxHP();
    int HP();
    int maxMP();
    int MP();
    int gnrAtk();
    int mgcAtk();
    int mgcCost();
    int healing();
    int healCost();
   
}