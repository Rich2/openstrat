/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import java.util.{GregorianCalendar => JGreg}

/** Date class, name may change. */
class Date(intUnsafe: Int) extends Ordered[Date]
{
  def minute: Int = intUnsafe %% 60
  def hour: Int = (intUnsafe %% 1440) / 60
  def day: Int = (intUnsafe %% 44640) / 1440
  def monthNum: Int = (intUnsafe %% 535680) / 44640
  def year = intUnsafe / 535680
  override def compare(that: Date): Int = year match
  { case y if y > that.year => 1
    case y if y < that.year => -1
    case _ if monthNum > that.monthNum => 1
    case _ if monthNum < that.monthNum => -1
    case _ if day > that.day => 1
    case _ if day < that.day => -1
    case _ => 0
  }

  private def monthStr: String = monthNum match
  { case 1 => "January"
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

  override def toString: String = year.str -- monthStr -- day.str
}

object Date
{
  def apply(year: Int, month: Int = 1, day: Int = 1, hour: Int = 0, minute: Int = 0, second: Int = 0): Date =
    new Date(minute + hour * 60 + day * 1440 + month * 44640 + year * 535680)
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
