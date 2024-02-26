package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
    private final CANSparkMax m_frontLeftMotor = new CANSparkMax(DrivetrainConstants.kFrontLeftMotor, MotorType.kBrushless);
    private final CANSparkMax m_frontRightMotor =  new CANSparkMax(DrivetrainConstants.kFrontRightMotor, MotorType.kBrushless);
    private final CANSparkMax m_rearLeftMotor =  new CANSparkMax(DrivetrainConstants.kRearLeftMotor, MotorType.kBrushless);
    private final CANSparkMax m_rearRigtMotor = new CANSparkMax(DrivetrainConstants.kRearRightMotor, MotorType.kBrushless);   
    private final DifferentialDrive m_drive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);

    public Drivetrain() {
        m_rearLeftMotor.follow(m_frontLeftMotor);
        m_rearRigtMotor.follow(m_frontRightMotor);
        m_frontLeftMotor.setInverted(true);
    }

    public Command arcadeDrive (DoubleSupplier speedProvider, DoubleSupplier rotProvider) {
        return run(() -> {
            double speed = speedProvider.getAsDouble();
            double rot = rotProvider.getAsDouble();
            
            m_drive.arcadeDrive(speed, rot, true);
        });
    }

    public void stop(){
        m_drive.arcadeDrive(0, 0);
    }
}
