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

public class BackoffRotateReloadAdaptive extends CommandGroup {
  public BackoffRotateReloadAdaptive() {
    if (!Robot.isTeleop) {
      if (Robot.autoSideFaceCargoShip) {
        if (Robot.autoSideLeft) {
          addSequential(new PIDDriveInches(46, true));
          addSequential(new PIDDriveRotateCustom(103, true));
        } else if (Robot.autoSideRight) {
          addSequential(new PIDDriveInches(46, true));
          addSequential(new PIDDriveRotateCustom(-103, true));
        }
      } else if (Robot.autoRocket) {
        if (Robot.autoSideLeft) {
          addSequential(new PIDDriveInches(15, true));
          addSequential(new PIDDriveRotateCustom(-150, true));
        } else if (Robot.autoSideRight) {
            addSequential(new PIDDriveInches(15, true));
            addSequential(new PIDDriveRotateCustom(150, true));
        }
      } else if (Robot.autoFrontFaceCargoShip) {
        if (Robot.autoSideLeft) {
          addSequential(new PIDDriveInches(10, true));
          addSequential(new PIDDriveRotateCustom(-150, true));
        } else if (Robot.autoSideRight) {
          addSequential(new PIDDriveInches(10, true));
          addSequential(new PIDDriveRotateCustom(150, true));
        }
      }
  
    }
  }
}
