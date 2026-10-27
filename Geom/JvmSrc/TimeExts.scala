/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import java.time.*, java.util.Calendar

extension (thisInstant: Instant)
{
  def y2mdhm: String ={
    val sTime = thisInstant.toString
    val st0: String = sTime.toString
    val sYear = st0.drop(2).take(2)
    val sMonth = st0.drop(5).take(2)
    val sDay = st0.drop(8).take(2)
    val sHour = st0.drop(11).take(2)
    val sMin = st0.drop(14).take(2)
    sYear + sMonth + sDay + sHour + sMin
  }
}