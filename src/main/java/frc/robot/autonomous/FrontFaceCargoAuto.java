/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;

public class FrontFaceCargoAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public FrontFaceCargoAuto() {
    addSequential(new PIDDriveInches(40, false));
    //addSequential(new )
  }
}
