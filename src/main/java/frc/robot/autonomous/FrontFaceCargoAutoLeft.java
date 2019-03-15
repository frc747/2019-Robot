/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDHatchMechanism;
import frc.robot.commands.TeleopSimulator;

public class FrontFaceCargoAutoLeft extends CommandGroup {
  /**
   * Add your docs here.
   */
  public FrontFaceCargoAutoLeft() {
    addSequential(new TeleopSimulator());
    addSequential(new PIDHatchMechanism(935, false));
    addSequential(new PIDDriveInches(20, true));
    addSequential(new PIDDriveRotateCustom(-120));
    // addSequential(new TeleopSimulator());
    // addSequential(n)
  }
}
