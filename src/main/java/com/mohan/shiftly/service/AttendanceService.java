package com.mohan.shiftly.service;

import com.mohan.shiftly.dto.GenericResDto;
import com.mohan.shiftly.entity.Attendance;
import com.mohan.shiftly.entity.User;
import com.mohan.shiftly.exception.ClockOutEx;
import com.mohan.shiftly.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepo;
    private final Utilities utilities;

    public GenericResDto clockInAndClockOut(){
        User user=utilities.getLoggedInUser();

        GenericResDto response=new GenericResDto();

        Optional<Attendance> existing=attendanceRepo.findByUserIdAndAttendanceDate(user.getId(), LocalDate.now());

        if(existing.isPresent()){
            Attendance attendance=existing.get();

            LocalTime clockIn= attendance.getClockInTime();
            LocalTime clockOut=LocalTime.now();
            Duration diff=Duration.between(clockIn,clockOut);

            if(diff.toSeconds()<60){
                throw new ClockOutEx("You need to wait at least 60 seconds to clock out");
            }

            long hours=diff.toHours();
            long minutes=diff.toMinutes()%60;




            attendance.setClockOutTime(clockOut);
            double workingHours = hours + (minutes / 60.0);

            attendance.setTotalWorkingHours(workingHours);

            attendanceRepo.save(attendance);

            response.setMessage("Clock out successfully");

        }else{
            Attendance attendance=new Attendance();
            attendance.setAttendanceDate(LocalDate.now());
            attendance.setUser(user);
            attendance.setClockInTime(LocalTime.now());

            attendanceRepo.save(attendance);

            response.setMessage("Clocked in successfully");

        }




        return response;


    }
}
