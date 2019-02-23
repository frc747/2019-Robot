package frc.robot.commands;

import frc.robot.OI;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDDriveRotate extends PIDCommand {

    private double angleToRotate;
    
    private int onTargetCount;
    
private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Multiply TARGET_COUNT_ONE_SECOND by the amount of time that you want for your minimum count threshold
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * 0.1;
    
    private final static double STOP_THRESHOLD_DEGREES = 5;//4.25
    private final static double MAX_PERCENT_VBUS = 1.0;
    
    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.10;
    
    private static final int timeoutMs = 10;
    
    public PIDDriveRotate(double degreesRotate) {
        super(0.03, 0.0, 0.01);
        
        this.angleToRotate = degreesRotate;
        
        requires(Robot.DRIVE_SUBSYSTEM);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        Robot.resetNavXAngle();
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        onTargetCount = 0;
        
        getPIDController().setInputRange(-180, 180);
        getPIDController().setOutputRange(-MAX_PERCENT_VBUS, MAX_PERCENT_VBUS);
        getPIDController().setAbsoluteTolerance(STOP_THRESHOLD_DEGREES); //the threshold that the PID Controller abides by to consider the value as "on target"
        getPIDController().setContinuous(true); //will reset back to the minimum value after reaching the max value

        
        getPIDController().setSetpoint(angleToRotate);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (getPIDController().onTarget()) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
                    
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }

    // Called once after isFinished returns true
    protected void end() {
        SmartDashboard.putNumber("Last Known NavX Angle:", Robot.getNavXAngle());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

    @Override
    protected double returnPIDInput() {
        return Robot.getNavXAngle();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.DRIVE_SUBSYSTEM.set(output, -output);
    }
}
