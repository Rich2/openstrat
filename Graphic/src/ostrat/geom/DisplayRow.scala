/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is not well documented. */
case class DisplayRow(margin: Double, subjs: Arr[GraphicSubject])
{ /** Horrible code. Needs rewriting. */
  def fromLeft(leftPt: Vec2): Arr[GraphicSubject] =
  {
    var acc: Buff[GraphicSubject] = newBuff()
    var xAcc: Double = leftPt.x + margin
    subjs.foreach { head =>
      acc += head.slateX(x + head.width / 2)//.asInstanceOf[GraphicSubject]
      x += head.width + margin
    }
    acc.toArr
  }  
}