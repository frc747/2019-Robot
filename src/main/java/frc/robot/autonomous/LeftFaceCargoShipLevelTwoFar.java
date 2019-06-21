/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class LeftFaceCargoShipLevelTwoFar extends CommandGroup {
  
  @Override
  protected void initialize() {
    Robot.autoSideLeft = true;
    Robot.autoSideRight = false;
    Robot.autoSideFaceCargoShip = true;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = false;

    Robot.operatorControl = false;
    Robot.resetNavXAngle();
  }

  // this autonomous routine runs assuming the robot starts at the furthest to the right and forward on the right side of level two
  public LeftFaceCargoShipLevelTwoFar() {
    
    // addSequential(new PauseCommand(4));
    //uncomment below BEFORE MIDKNIGHT
    addSequential(new PIDDriveInchesHoldHatch(109.775, false), 4);
    addSequential(new PIDDriveRotateCustom(-21.5, false), 4);
    addSequential(new PIDDriveInches(140.5, false), 4); //was 143 when it overdrove in practice match
    addSequential(new PIDDriveRotateCustom(0, false), 4);
    addSequential(new PIDDriveInches(43.5, false), 4); //was 143 when it overdrove in practice match
    addSequential(new PIDDriveRotateCustom(90, false), 4);
    // driver takes over now
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}