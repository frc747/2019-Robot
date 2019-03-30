package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDHatchMechanism;
import frc.robot.commands.TeleopSimulator;


public class FrontFaceCargoShipLeft extends CommandGroup {

  @Override
  protected void initialize() {
    Robot.autoSideLeft = true;
    Robot.autoSideRight = false;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = true;
    Robot.autoRocket = false;
    Robot.operatorControl = false;
    Robot.resetNavXAngle();
  }

  public FrontFaceCargoShipLeft() {
    // addSequential(new TeleopSimulator());
    // addSequential(new PIDHatchMechanism(935, false));
    // addSequential(new PIDDriveInches(20, true));
    // addSequential(new PIDDriveRotateCustom(-120));


    // addSequential(new TeleopSimulator());
    // addSequential(new PIDDriveInches(20, true);
    // addSequential(new PIDDriveRotateCustom(180));
    // addSequential(new TeleopSimulator());
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
