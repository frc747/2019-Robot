/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDDriveInchesArc;
import frc.robot.commands.PIDDriveRotate;
import frc.robot.commands.PIDDriveRotateCustom;

public class RocketAutonomousArc extends CommandGroup {
  /**
   * Add your docs here.
   */
  public RocketAutonomousArc() {
    addSequential(new PIDDriveRotateCustom(-27));
    addSequential(new PIDDriveInches(177, false));
  }
}
