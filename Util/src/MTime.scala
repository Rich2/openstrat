/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag//java.util.{GregorianCalendar => JGreg}

/** An instant of time specified to the nearest minute. By default uees Gregorian Calender */
class MTime(val int1: Int) extends AnyVal with Ordered[MTime] with Int1Elem
{ import MTime.lastMonthDay
  def minute: Int = int1 %% 60
  def hour: Int = (int1 %% 1440) / 60
  private def dayInt: Int = (int1 %% 44640) / 1440
  def dayNum: Int = dayInt + 1

  /** A 0 based month number. 0 == January, 11 == December. Use monthNum for standard month ordering.  */
  private def monthInt: Int = (int1 %% 535680) / 44640

  /** The month number. 1 == January, 12 == December. */
  def monthNum: Int= monthInt + 1

  def yearInt: Int = int1 match{
    case n if n < 0 & int1 %% 535680 > 0 => int1 / 535680 - 1
    case n => int1 / 535680
  }
  override def compare(that: MTime): Int = int1 match
  { case i if i > that.int1 => 1
    case i if i == that.int1 => 0
    case _ => -1
  }

  def monthStr: String = monthInt match
  { case 0 => "January"
    case 1 => "Febuary"
    case 2 => "March"
    case 3 => "April"
    case 4 => "May"
    case 5 => "June"
    case 6 => "July"
    case 7 => "August"
    case 8 => "September"
    case 9 => "October"
    case 10 => "November"
    case 11 => "December"
    case n => excep(s"$n is an unknown month number.")
  }

  def monthStr3: String = monthInt match
  { case 0 => "Jan"
    case 1 => "Feb"
    case 2 => "Mar"
    case 3 => "Apr"
    case 4 => "May"
    case 5 => "Jun"
    case 6 => "Jul"
    case 7 => "Aug"
    case 8 => "Sep"
    case 9 => "Oct"
    case 10 => "Nov"
    case 11 => "Dec"
    case n => excep(s"$n is unknown month number.")
  }

  /** Produces a date [[String]] with month in 3 letter abbreviation. */
  def str3: String =
  { def bn: Int = -yearInt + 1
    val yearStr = yearInt match
    {
      case n if n >= 1000 => s"AD$n"
      case n if n >= 100 => s"AD$n "
      case n if n >= 10 => s"AD$n  "
      case n if n >= 1 => s"AD$n   "
      case n if n <= -999 => s"BC$bn"
      case n if n <= -99 => s"BC$bn "
      case n if n <= -9 => s"BC$bn  "
      case n => s"BC$bn   "
    }
    f"$yearStr $monthStr3 $dayNum%2d $hour%02d $minute%02d"}
  override def toString: String = yearInt.str -- monthStr -- dayNum.str

  /** Adds a year to the time. February 29th goes to February 28th. */
  def addYear: MTime = MTime(yearInt + 1, monthNum, dayNum.min(lastMonthDay(yearInt + 1, monthNum)), hour, minute)

  /** Adds the given number of years to the time. February 29th goes to February 28th. */
  def addYears(num: Int): MTime = MTime(yearInt + num, monthNum, dayNum.min(lastMonthDay(yearInt + num, monthNum)), hour, minute)

  /** Subtracts a year from the time. February 29th goes to February 28th. */
  def subYear: MTime = MTime(yearInt - 1, monthNum, dayNum.min(lastMonthDay(yearInt - 1, monthNum)), hour, minute)

  /** Subtracts the given number of years from the time. February 29th goes to February 28th. */
  def subYears(num: Int): MTime = MTime(yearInt - num, monthNum, dayNum.min(lastMonthDay(yearInt - num, monthNum)), hour, minute)


  /** Adds a month to this [[MTime]]. If the new month does not contain the day number, the day number is reduced to the last dasy fp the month. Eg
   *  2023 May 31st goes to 2023 June 30th. */
  def addMonth: MTime = if(monthNum == 12) MTime(yearInt + 1, 1, dayNum, hour, minute)
    else MTime(yearInt, monthNum + 1, dayNum.min(lastMonthDay(yearInt, monthNum + 1)), hour, minute)

  def addMonths(num: Int): MTime = if (num < 0) subMonths(- num) else
  { val totalMonths = yearInt * 12 + monthInt + num
    val newYear = totalMonths / 12
    val newMonthNum = totalMonths %% 12 + 1
    MTime(newYear, newMonthNum, dayNum.min(lastMonthDay(newYear, newMonthNum)))
  }

  def subMonth: MTime = if (monthNum == 1) MTime(yearInt - 1, 12, dayNum, hour, minute)
  else MTime(yearInt, monthNum - 1, dayNum.min(lastMonthDay(yearInt, monthNum - 1)), hour, minute)

  def subMonths(num: Int): MTime = if (num < 0) addMonths(-num) else
  { val totalMonths = yearInt * 12 + monthInt - num
    val newYear = totalMonths / 12
    val newMonthNum = totalMonths %% 12 + 1
    MTime(newYear, newMonthNum, dayNum.min(lastMonthDay(newYear, newMonthNum)))
  }

  def addDay: MTime = monthNum match
  { case 11 if dayNum == 31 => MTime(yearInt + 1, 1, 1, monthNum, dayNum)
    case n if dayNum == lastMonthDay(yearInt, n) => MTime(yearInt, n + 1, 1, hour, minute)
    case n => MTime(yearInt, n, dayNum + 1, hour, minute)
  }

  /** Adds the give number of days to the [[MTime]]. This hasn't been tested. */
  def addDays(num: Int): MTime = if(num < 0) subDays(-num) else
  { val leaps = yearInt + (num / 1461) * 4
    val rem = num %% 1461

    /** Assumes the input [[MTime]] is on the last day of the month and rem > 0. */
    def loop(acc: MTime, rem: Int): MTime = acc.monthNum match
    { case 12 if rem > 31 => loop(MTime(acc.yearInt + 1, 1, 31, hour, minute), rem - 31)
      case 12 => MTime(acc.yearInt + 1, 1, rem, hour, minute)
      case n =>
      { val newLastDay = lastMonthDay(acc.yearInt, acc.monthNum + 1)
        if(rem > newLastDay) loop(MTime(acc.yearInt, acc.monthNum + 1, newLastDay, hour, minute), rem - newLastDay)
        else MTime(acc.yearInt, monthNum + 1, rem, hour, minute)
      }
    }

    val newLastDay: Int = lastMonthDay(leaps, monthNum)
    if (newLastDay >= dayNum + rem) MTime(leaps, monthNum, dayNum + rem)
    else loop(MTime(leaps, monthNum, newLastDay, hour, minute), rem - newLastDay + dayNum)
  }

  /** Returns a time one day earlier. */
  def subDay: MTime = dayNum match
  { case 1 if monthNum == 1 => MTime(yearInt - 1, 12, 31, hour, minute)
    case 1 => MTime(yearInt, monthNum - 1, lastMonthDay(yearInt, monthNum - 1), hour, minute)
    case n => MTime(yearInt, monthNum, n - 1, hour, minute)
  }

  /** Not correct yet. */
  def subDays(num: Int): MTime = if (num < 0) addDays(-num) else
  { val leaps = yearInt - (num / 1461) * 4
    val rem = num %% 1461

    /** Assumes the input [[MTime]] is on the 1st day of the month and rem > 0. */
    def loop(acc: MTime, rem: Int): MTime = acc.monthNum match
    { case 1 if rem > 31 => loop(MTime(acc.yearInt -1 , 12, 31, hour, minute), rem - 31)
      case 1 => MTime(acc.yearInt - 1, 12, 32 - rem, hour, minute)
      case n =>
      { val newMonth = acc.monthNum - 1
        val newLastDay = lastMonthDay(acc.yearInt, newMonth)
        if (newLastDay > rem) MTime(acc.yearInt, newMonth, newLastDay - rem + 1, hour, minute)
        else loop(MTime(acc.yearInt, newMonth, 1, hour, minute), rem - newLastDay)
      }
    }

    val newLastDay: Int = lastMonthDay(leaps, monthNum)

    if (dayNum > rem) MTime(leaps, monthNum, dayNum - rem)
    else loop(MTime(leaps, monthNum, 1, hour, minute), rem - dayNum + 1)
  }
}

object MTime
{
  def apply(year: Int, monthNum: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0): MTime =
    new MTime(minute + hour * 60 + (day - 1) * 1440 + (monthNum - 1) * 44640 + year * 535680)

  def lastMonthDay(yearInt: Int, monthNum: Int): Int = monthNum match
  { case 1 | 3 | 5 | 7 | 8| 10 | 11 | 12 => 31
    case 4 | 6 | 9 => 30
    case 2 if yearInt %% 100 == 0 & (yearInt / 100) %% 4 != 0 => 28
    case 2 if yearInt %% 4 == 0 => 29
    case 2 => 28
    case n => excep(n.str -- "is an invalid Month number.")
  }

  implicit val persistEv: PersistInt5[MTime] = PersistInt5[MTime]("MTime", "year", _.yearInt, "month", _.monthNum, "day", _.dayNum, "hour", _.hour,
    "minute", _.minute, (i1, i2, i3, i4, i5) => MTime(i1, i2, i3, i4, i5), Some(0), Some(0), Some(1), Some(1))
}

/** A time eriod. Compact class for holding 2 [[MTime]]s. */
class MTime2(val int1: Int, val int2: Int)
{ /** Start time. */
  def time1: MTime = new MTime(int1)

  /** End time. */
  def time2: MTime = new MTime(int2)
}

/** Companion object for [[MTime2]] class, contains apply factory method. */
object MTime2
{ /** Apply factory method for [[MTime2]] class.Constructs from 2 [[MTime]] classes. */
  def apply(time1: MTime, time2: MTime):MTime2 = new MTime2(time1.int1, time2.int1)
}

class MTimeSeries[A](val arrayInt: Array[Int], arrayA: Array[A])
{
  def seriesNum: Int = arrayA.length
  def find(time: MTime): Option[A] = if (time.int1 < arrayInt(0) | time.int1 > arrayInt(seriesNum)) None
  else
  { def loop(i: Int): Option[A] = if (time.int1 < arrayInt(i + 1)) Some(arrayA(i)) else loop(i + 1)
    loop(0)
  }
}

object MTimeSeries
{
  def apply[A](startTime: MTime, pairs: (MTime, A)*)(implicit ct: ClassTag[A]): MTimeSeries[A] =
  { val len = pairs.length
    val intArray = new Array[Int](len + 1)
    intArray(0) = startTime.int1
    val arrayA = new Array[A](len)
    var i = 0
    pairs.foreach{ pair =>
      intArray(i + 1) = pair._1.int1
      arrayA(i) = pair._2
      i += 1
    }
    new MTimeSeries[A](intArray, arrayA)
  }

  def apply[A](a1: A, pairs: (MTime, A)*)(implicit startEnd: MTime2, ct: ClassTag[A]): MTimeSeries[A] = {
    val len = pairs.length + 1
    val intArray = new Array[Int](len + 1)
    intArray(0) = startEnd.int1
    val arrayA = new Array[A](len)
    arrayA(0) = a1
    var i = 1
    pairs.foreach { pair =>
      intArray(i) = pair._1.int1
      arrayA(i) = pair._2
      i += 1
    }
    intArray(len) = startEnd.int2
    new MTimeSeries[A](intArray, arrayA)
  }
}

/*
object TimeSpan
{
   def apply(ticsInMilliSeconds: Long): TimeSpan = TimeSpan(ticsInMilliSeconds) 
}
class TimeSpan(val tics: Long) extends AnyVal
{
   def +(operand: TimeSpan): TimeSpan = TimeSpan(tics + operand.tics)
   def -(operand: TimeSpan): TimeSpan = TimeSpan(tics - operand.tics)
   def *(operand: Long): TimeSpan = TimeSpan(tics * operand)
   def *(operand: Double): TimeSpan = TimeSpan((tics * operand).toLong)
   def /(operand: Long): TimeSpan = TimeSpan(tics / operand)
   def /(operand: Double): TimeSpan = TimeSpan((tics / operand).toLong)
   def toSecsonds: Long = tics / 1000
   def toMinutes: Long = tics / 60000
   def toHours: Long = tics / 3600000
}
object TimeDate
{
   def apply(ticsInMilliSecondsRelativeTo1970: Long): TimeDate = new TimeDate(ticsInMilliSecondsRelativeTo1970)
   def apply(year: Int, month: Int, day: Int) = new TimeDate(new JGreg(year, month, day).getTimeInMillis())   
}

class TimeDate(val tics: Long) extends AnyVal
{
   private def mutT: JGreg = new JGreg(1, 1, 1)
   def +(operand: TimeSpan): TimeDate = TimeDate(tics + operand.tics)
   def -(operand: TimeSpan): TimeDate = TimeDate(tics - operand.tics)
   def --(operand: TimeDate): TimeSpan = TimeSpan(tics - operand.tics)
   def date: String =
   {
      val m = mutT
      m.setTimeInMillis(tics)
      m.get(1).toString + " " + getJGEra(m) + " " + getJGMonth(m) + " " + m.get(4).toString      
   }
   private def getJGMonth(jg: JGreg): String = jg.get(2) match
   {
      case 1 => "January"
      case 2 => "Febuary"
      case 3 => "March"
      case 4 => "April"
      case 5 => "May"
      case 6 => "June"
      case 7 => "July"
      case 8 => "August"
      case 9 => "September"
      case 10 => "October"
      case 11 => "Novemeber"
      case 12 => "December"   
      case _ => "Unknown Month"   
   }
   private def getJGEra(jg: JGreg): String = jg.get(0) match
   {
      case 1 => "AD"
      case v => v.toString   
   }      
}

object Hour
{
   def apply: TimeSpan = new TimeSpan(3600000)
   def apply(numberOfWholeHours: Long): TimeSpan = new TimeSpan(numberOfWholeHours * 3600000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 3600000).toLong)
}
object Minute
{
   def apply: TimeSpan = new TimeSpan(60000)
   def apply(numberOfWholeMinutes: Long): TimeSpan = new TimeSpan(numberOfWholeMinutes * 60000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 60000).toLong)
}
object Second
{ 
   def apply: TimeSpan = new TimeSpan(1000)
   def apply(numberOfWholeSeconds: Long): TimeSpan = new TimeSpan(numberOfWholeSeconds * 1000)
   def inDou(value: Double): TimeSpan = new TimeSpan((value * 1000).toLong)
}

object WholeHours
{
	def apply(numberOfWholeHours: Int): WholeHours = new WholeHours(numberOfWholeHours)
}
class WholeHours(val value: Int) extends AnyVal
{
   def +(operand: WholeHours): WholeHours = new WholeHours(value + operand.value)
   def toTimeSpan: TimeSpan = new TimeSpan(value * 3600000)
}*/