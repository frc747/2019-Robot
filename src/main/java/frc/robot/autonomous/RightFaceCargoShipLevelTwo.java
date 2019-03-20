/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class RightFaceCargoShipLevelTwo extends CommandGroup {


  // this autonomous routine runs assuming the robot starts at the furthest to the right and forward on the right side of level two
  public RightFaceCargoShipLevelTwo() {
    addSequential(new PIDDriveInchesHoldHatch(96.775, false)); //was 96.775 at the end of Chestnut Hill Qualification Matches
    addSequential(new PIDDriveRotateCustom(18)); // was 13.5 at the end of Chestnut Hill Qualification Matches
    addSequential(new PIDDriveInches(146, false)); // was 146.5 in first elims match // was 143 at the end of Chestnut Hill Qualification Matches
    addSequential(new PIDDriveRotateCustom(-100)); // was -108 in first elims match // was -100 at the end of Chestnut Hill Qualification Matches
   //THE CHANGES MADE AFTER THE FIRST ELIMS MATCH WERE BASED OFF OF THE LEFT SIDE
   



    // addSequential(new PIDDriveRotateCustom(45));
    // addSequential(new PIDDriveInches(52, false));
    // addSequential(new PIDDriveRotateCustom(-45));
    // addSequential(new LineTrackCommandAuto(5));
  }

}