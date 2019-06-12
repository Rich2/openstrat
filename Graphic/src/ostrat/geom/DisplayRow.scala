/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is not well documented. */
case class DisplayRow(margin: Double, subjs: Arr[GraphicSubject])
{ /** Horrible code. Needs rewriting. */
  def fromLeft(leftPt: Vec2): Arr[GraphicSubject] = subjs.mapWith1(leftPt.x + margin)((head, x) =>
    (head.slateX(x + head.width / 2),//.asInstanceOf[GraphicSubject]
      x + head.width + margin))
}