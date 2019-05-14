package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;


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
  }

  @Override
  protected void end() {
    Robot.operatorControl = true;
  }
}
