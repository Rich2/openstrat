/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package geom
import pweb.*

/** Trait that gives the time of day to varying levels od precision */
trait TimeOfDay extends TimeHtml

/** Time of day to the minute. */
case class MinOfDay(hourInt: Int, minInt: Int) extends TimeOfDay
{ override def dtAtt = MinOfDayAtt(hourInt, minInt)
  override def contents: RArr[XCon] = RArr(dtAtt.valueStr)
}