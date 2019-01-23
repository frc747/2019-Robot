/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.*;
public class RocketAutonomous extends CommandGroup {
  /**
   * Add your docs here.
   */
  public RocketAutonomous() {
    Robot.resetNavXAngle();
    addSequential(new PIDDriveInches(90));
    addSequential(new PIDDriveRotate(90));
    //addSequential(new PIDDriveInches(-70));
    //addSequential(new PIDDriveRotate(55));
  }
}
