/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML time element. */
trait TimeHtml extends HtmlInedit
{ override def tagName: String = "time"
  def dtAtt: DateTimeAtt
  override def attribs: RArr[XAtt] = RArr(dtAtt)
}

/** DateTime attribute. */
trait DateTimeAtt extends XAttShort
{ override def name: String = "datetime"
}

/** A date or date and time attribute. */
trait DatePlusAtt extends DateTimeAtt
{
  def yearInt: Int
  def monthInt: Int
  def dayInt: Int

  def yStr = f"$yearInt%04d"
  def monthStr = f"$monthInt%02d"
  def dStr = f"$dayInt%02d"
}

/** DateTime attribute for the YYYY-MM-DD syntax. */
case class DateAtt(yearInt: Int, monthInt: Int, dayInt: Int) extends DatePlusAtt
{ override def valueStr: String = s"$yStr:$monthStr:$dStr"
}

/** DateTime attribute for the YYYY-MM-DD HH:MM syntax. */
case class DateMinAtt(yearInt: Int, monthInt: Int, dayInt: Int, hourInt: Int, minInt: Int) extends DatePlusAtt
{ def hStr = f"$hourInt%02d"
  def minStr = f"$minInt%02d"
  override def valueStr: String =s"$yStr:$monthStr:$dStr $hourInt:$minInt"
}