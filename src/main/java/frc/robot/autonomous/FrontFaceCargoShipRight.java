package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDHatchMechanism;
import frc.robot.commands.TeleopSimulator;
import frc.robot.commands.PIDDriveRotateCustom;
public class FrontFaceCargoShipRight extends CommandGroup {

  @Override
  protected void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = true;
    Robot.autoRocket = false;
    Robot.operatorControl = false;
    Robot.resetNavXAngle();
  }

  public FrontFaceCargoShipRight() {
    // addSequential(new TeleopSimulator());
    // addSequential(new PIDHatchMechanism(935, false));
    // addSequential(new PIDDriveInches(20, true));
    // addSequential(new PIDDriveRotateCustom(120));

    
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
