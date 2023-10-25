package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel(){

    // this function will return List<Schedule> from database using scheduleDao.getAll()
    fun fullSchedule(): Flow<List<Schedule>> = scheduleDao.getAll()

    fun scheduleForStopName(name:String): Flow<List<Schedule>> {
        return scheduleDao.getByStopName(name)
    }

}

/**you can't just instantiate a BusScheduleViewModel directly and expect everything to work.
 * As the ViewModel class BusScheduleViewModel is meant to be lifecycle aware,
 * it should be instantiated by an object that can respond to lifecycle events.
 * If you instantiate it directly in one of your fragments,
 * then your fragment object will have to handle everything, including all the memory management,
 * which is beyond the scope of what your app's code should do.
 * Instead, you can create a class, called a factory, that will instantiate view model objects for you.
 *
 * To create a factory, below the view model class, create a new class BusScheduleViewModelFactory,
 * that inherits from ViewModelProvider.Factory.*/

class BusScheduleViewModelFactory(private val scheduleDao: ScheduleDao): ViewModelProvider.Factory{

    /**You'll just need a bit of boilerplate code to correctly instantiate a view model.
     * Instead of initializing the class directly, you'll override a method called create() method
     * that returns a BusScheduleViewModelFactory with some error checking.
     * Implement the create() inside the BusScheduleViewModelFactory class as follows.*/
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    /**You can now instantiate a BusScheduleViewModelFactory object with BusScheduleViewModelFactory.create(),
     * so that your view model can be lifecycle aware without your fragment having to handle this directly.
     */
}