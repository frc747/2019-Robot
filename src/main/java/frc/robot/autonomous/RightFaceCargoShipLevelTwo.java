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

public class RightFaceCargoShipLevelTwo extends CommandGroup {

  @Override
  protected void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = true;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = false;

    Robot.operatorControl = false;
  }

  // this autonomous routine runs assuming the robot starts at the furthest to the right and forward on the right side of level two
  public RightFaceCargoShipLevelTwo() {
    addSequential(new PIDDriveInchesHoldHatch(109.775, false), 4);
    addSequential(new PIDDriveRotateCustom(21.5, false), 4);
    addSequential(new PIDDriveInches(142, false), 4);
    addSequential(new PIDDriveRotateCustom(-90, false), 4);
    // driver takes over now
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}