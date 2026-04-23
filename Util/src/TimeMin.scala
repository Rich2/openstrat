/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag//java.util.{GregorianCalendar => JGreg}

trait TimeApprox extends Any
{
  def long1: Long

  private def dayInt: Int = ((long1 %% 44640) / 1440).toInt

  def dayNum: Int = dayInt + 1

  def yearInt: Int =
  { val res = long1 match
    { case n if n < 0 & long1 %% 535680 > 0 => long1 / 535680 - 1
      case n => long1 / 535680
    }
    res.toInt
  }

  /** A 0 based month number. 0 == January, 11 == December. Use monthNum for standard month ordering. */
  protected def monthInt: Int = ((long1 %% 535680) / 44640).toInt

  /** The month number. 1 == January, 12 == December. */
  def monthNum: Int = monthInt + 1

  def monthStr: String = monthInt match {
    case 0 => "January"
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
}

class TimeDay(val long1: Long) extends AnyVal, Ordered[TimeDay], Long1Elem, TimeApprox
{
  override def compare(that: TimeDay): Int = ???

  /** Produces a date [[String]] with month in 3 letter abbreviation. */
  def str3: String =
  {
    def bn: Int = -yearInt + 1

    val yearStr = yearInt match
    { case n if n >= 1000 => s"AD$n"
      case n if n >= 100 => s"AD$n "
      case n if n >= 10 => s"AD$n  "
      case n if n >= 1 => s"AD$n   "
      case n if n <= -999 => s"BC$bn"
      case n if n <= -99 => s"BC$bn "
      case n if n <= -9 => s"BC$bn  "
      case n => s"BC$bn   "
    }
    f"$yearStr $monthStr3 $dayNum%2d"
  }

  override def toString: String = yearInt.str -- monthStr -- dayNum.str
}

object TimeDay
{
  def apply(year: Int, monthNum: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0): TimeDay =
    new TimeDay(minute + hour * 60 + (day - 1) * 1440 + (monthNum - 1) * 44640 + year * 535680)
}


/** An instant of time specified to the nearest minute. By default, uses Gregorian Calendar */
class TimeMin(val long1: Long) extends AnyVal, Ordered[TimeMin], Long1Elem, TimeApprox
{ import TimeMin.lastMonthDay
  def minute: Int = (long1 %% 60).toInt
  def hour: Int = ((long1 %% 1440) / 60).toInt


  override def compare(that: TimeMin): Int = long1 match
  { case i if i > that.long1 => 1
    case i if i == that.long1 => 0
    case _ => -1
  }

  /** Adds a year to the time. February 29th goes to February 28th. */
  def addYear: TimeMin = TimeMin(yearInt + 1, monthNum, dayNum.min(lastMonthDay(yearInt + 1, monthNum)), hour, minute)

  /** Adds the given number of years to the time. February 29th goes to February 28th. */
  def addYears(num: Int): TimeMin = TimeMin(yearInt + num, monthNum, dayNum.min(lastMonthDay(yearInt + num, monthNum)), hour, minute)

  /** Subtracts a year from the time. February 29th goes to February 28th. */
  def subYear: TimeMin = TimeMin(yearInt - 1, monthNum, dayNum.min(lastMonthDay(yearInt - 1, monthNum)), hour, minute)

  /** Subtracts the given number of years from the time. February 29th goes to February 28th. */
  def subYears(num: Int): TimeMin = TimeMin(yearInt - num, monthNum, dayNum.min(lastMonthDay(yearInt - num, monthNum)), hour, minute)

  /** Adds a month to this [[TimeMin]]. If the new month does not contain the day number, the day number is reduced to the last dasy fp the month. Eg 2023 May
   * 31st goes to 2023 June 30th. */
  def addMonth: TimeMin = if(monthNum == 12) TimeMin(yearInt + 1, 1, dayNum, hour, minute)
    else TimeMin(yearInt, monthNum + 1, dayNum.min(lastMonthDay(yearInt, monthNum + 1)), hour, minute)

  def addMonths(num: Int): TimeMin = if (num < 0) subMonths(- num) else
  { val totalMonths = yearInt * 12 + monthInt + num
    val newYear = totalMonths / 12
    val newMonthNum = totalMonths %% 12 + 1
    TimeMin(newYear, newMonthNum, dayNum.min(lastMonthDay(newYear, newMonthNum)))
  }

  def subMonth: TimeMin = if (monthNum == 1) TimeMin(yearInt - 1, 12, dayNum, hour, minute)
  else TimeMin(yearInt, monthNum - 1, dayNum.min(lastMonthDay(yearInt, monthNum - 1)), hour, minute)

  def subMonths(num: Int): TimeMin = if (num < 0) addMonths(-num) else
  { val totalMonths = yearInt * 12 + monthInt - num
    val newYear = totalMonths / 12
    val newMonthNum = totalMonths %% 12 + 1
    TimeMin(newYear, newMonthNum, dayNum.min(lastMonthDay(newYear, newMonthNum)))
  }

  def addDay: TimeMin = monthNum match
  { case 11 if dayNum == 31 => TimeMin(yearInt + 1, 1, 1, monthNum, dayNum)
    case n if dayNum == lastMonthDay(yearInt, n) => TimeMin(yearInt, n + 1, 1, hour, minute)
    case n => TimeMin(yearInt, n, dayNum + 1, hour, minute)
  }

  /** Adds the give number of days to the [[TimeMin]]. This hasn't been tested. */
  def addDays(num: Int): TimeMin = if(num < 0) subDays(-num) else
  { val leaps = yearInt + (num / 1461) * 4
    val rem = num %% 1461

    /** Assumes the input [[TimeMin]] is on the last day of the month and rem > 0. */
    def loop(acc: TimeMin, rem: Int): TimeMin = acc.monthNum match
    { case 12 if rem > 31 => loop(TimeMin(acc.yearInt + 1, 1, 31, hour, minute), rem - 31)
      case 12 => TimeMin(acc.yearInt + 1, 1, rem, hour, minute)
      case n =>
      { val newLastDay = lastMonthDay(acc.yearInt, acc.monthNum + 1)
        if(rem > newLastDay) loop(TimeMin(acc.yearInt, acc.monthNum + 1, newLastDay, hour, minute), rem - newLastDay)
        else TimeMin(acc.yearInt, monthNum + 1, rem, hour, minute)
      }
    }

    val newLastDay: Int = lastMonthDay(leaps, monthNum)
    if (newLastDay >= dayNum + rem) TimeMin(leaps, monthNum, dayNum + rem)
    else loop(TimeMin(leaps, monthNum, newLastDay, hour, minute), rem - newLastDay + dayNum)
  }

  /** Returns a time one day earlier. */
  def subDay: TimeMin = dayNum match
  { case 1 if monthNum == 1 => TimeMin(yearInt - 1, 12, 31, hour, minute)
    case 1 => TimeMin(yearInt, monthNum - 1, lastMonthDay(yearInt, monthNum - 1), hour, minute)
    case n => TimeMin(yearInt, monthNum, n - 1, hour, minute)
  }

  /** Not correct yet. */
  def subDays(num: Int): TimeMin = if (num < 0) addDays(-num) else
  { val leaps = yearInt - (num / 1461) * 4
    val rem = num %% 1461

    /** Assumes the input [[TimeMin]] is on the 1st day of the month and rem > 0. */
    def loop(acc: TimeMin, rem: Int): TimeMin = acc.monthNum match
    { case 1 if rem > 31 => loop(TimeMin(acc.yearInt -1 , 12, 31, hour, minute), rem - 31)
      case 1 => TimeMin(acc.yearInt - 1, 12, 32 - rem, hour, minute)
      case n =>
      { val newMonth = acc.monthNum - 1
        val newLastDay = lastMonthDay(acc.yearInt, newMonth)
        if (newLastDay > rem) TimeMin(acc.yearInt, newMonth, newLastDay - rem + 1, hour, minute)
        else loop(TimeMin(acc.yearInt, newMonth, 1, hour, minute), rem - newLastDay)
      }
    }

    val newLastDay: Int = lastMonthDay(leaps, monthNum)

    if (dayNum > rem) TimeMin(leaps, monthNum, dayNum - rem)
    else loop(TimeMin(leaps, monthNum, 1, hour, minute), rem - dayNum + 1)
  }

  /** Produces a date [[String]] with month in 3 letter abbreviation. */
  def str3: String =
  { def bn: Int = -yearInt + 1

    val yearStr = yearInt match {
      case n if n >= 1000 => s"AD$n"
      case n if n >= 100 => s"AD$n "
      case n if n >= 10 => s"AD$n  "
      case n if n >= 1 => s"AD$n   "
      case n if n <= -999 => s"BC$bn"
      case n if n <= -99 => s"BC$bn "
      case n if n <= -9 => s"BC$bn  "
      case n => s"BC$bn   "
    }
    f"$yearStr $monthStr3 $dayNum%2d $hour%02d $minute%02d"
  }

  override def toString: String = yearInt.str -- monthStr -- dayNum.str
}

object TimeMin
{
  def apply(year: Int, monthNum: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0): TimeMin =
    new TimeMin(minute + hour * 60 + (day - 1) * 1440 + (monthNum - 1) * 44640 + year * 535680)

  def lastMonthDay(yearInt: Int, monthNum: Int): Int = monthNum match
  { case 1 | 3 | 5 | 7 | 8| 10 | 11 | 12 => 31
    case 4 | 6 | 9 => 30
    case 2 if yearInt %% 100 == 0 & (yearInt / 100) %% 4 != 0 => 28
    case 2 if yearInt %% 4 == 0 => 29
    case 2 => 28
    case n => excep(n.str -- "is an invalid Month number.")
  }

  given unshowEv: UnshowInt5[TimeMin] = UnshowInt5[TimeMin]("MTime", "year", "month", "day", "hour", "minute",
    (i1, i2, i3, i4, i5) => TimeMin(i1, i2, i3, i4, i5), Some(0), Some(0), Some(1), Some(1))
}

/** A time period. Compact class for holding 2 [[TimeMin]]s. */
class TimeMin2(val long1: Long, val long2: Long)
{ /** Start time. */
  def time1: TimeMin = new TimeMin(long1)

  /** End time. */
  def time2: TimeMin = new TimeMin(long2)
}

/** Companion object for [[TimeMin2]] class, contains apply factory method. */
object TimeMin2
{ /** Apply factory method for [[TimeMin2]] class.Constructs from 2 [[TimeMin]] classes. */
  def apply(time1: TimeMin, time2: TimeMin):TimeMin2 = new TimeMin2(time1.long1, time2.long1)
}

/** A time period. Compact class for holding 2 [[TimeMin]]s. */
class MTime2Opt(val long1: Long, val hasEnd: Boolean, val long2: Long)
{ /** Start time. */
  def time1: TimeMin = new TimeMin(long1)

  /** End time. */
  def time2: Option[TimeMin] = ifSome(hasEnd, new TimeMin(long2))
}

object MTime2Opt
{ /** Apply factory method for [[TimeMin2]] class.Constructs from 2 [[TimeMin]] classes. */
  def apply(time1: TimeMin, oTime2: Option[TimeMin]):MTime2Opt = oTime2 match
  { case Some(time2) => new MTime2Opt(time1.long1, true, time2.long1)
    case None => new MTime2Opt(time1.long1, false, 0)
  }
}

/** Matches values of type A to periods in historical time. Each value has a start time. It is optional if there is a final end time, allowing the final value
 * of the series to continue into the indefinite future. For example in principle we know the start date of each of our Prime Minsters, but we can never know
 * for certain the end date of the current Prime Minister.  */
class TimeMinSeries[A](val arrayLong: Array[Long], arrayA: Array[A])
{
  def seriesNum: Int = arrayA.length

  def endDate: Boolean = arrayLong.length - seriesNum match
  { case 0 => false
    case 1 => true
    case _ => excep("MTImeSeries incorrect Array lengths")
  }

  def find(time: TimeMin): Option[A] = if (time.long1 < arrayLong(0) || endDate && time.long1 > arrayLong.last) None
  else
  { def loop(i: Int): Option[A] = if (time.long1 < arrayLong(i + 1)) Some(arrayA(i)) else loop(i + 1)
    loop(0)
  }

  def get(time: TimeMin): A = find(time).get
}

object TimeMinSeries
{
  def ended[A](startTime: TimeMin, pairs: (TimeMin, A)*)(implicit ct: ClassTag[A]): TimeMinSeries[A] =
  { val len = pairs.length
    val longArray = new Array[Long](len + 1)
    longArray(0) = startTime.long1
    val arrayA = new Array[A](len)
    var i = 0
    pairs.foreach{ pair =>
      longArray(i + 1) = pair._1.long1
      arrayA(i) = pair._2
      i += 1
    }
    new TimeMinSeries[A](longArray, arrayA)
  }

  def apply[A](a1: A, pairs: (TimeMin, A)*)(implicit startEnd: MTime2Opt, ct: ClassTag[A]): TimeMinSeries[A] =
  { val len = pairs.length + 1
    val lenI = ife(startEnd.hasEnd, len + 1, len)
    val longArray = new Array[Long](len + 1)
    longArray(0) = startEnd.long1
    val arrayA = new Array[A](len)
    arrayA(0) = a1
    var i = 1
    pairs.foreach { pair =>
      longArray(i) = pair._1.long1
      arrayA(i) = pair._2
      i += 1
    }
    if (startEnd.hasEnd) {longArray(len) = startEnd.long2}
    new TimeMinSeries[A](longArray, arrayA)
  }
}