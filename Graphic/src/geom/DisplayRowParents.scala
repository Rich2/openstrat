/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Creates a row of GraphicSubject. Not sure about the usefulness of this specific class, or its implementation*/
case class DisplayRowParents(margin: Double, subjs: Refs[GraphicParent])
{ /** Horrible code. Needs rewriting. */
  def fromLeft(leftPt: Vec2): Refs[GraphicParent] = subjs.mapWithAcc(leftPt.x + margin)((head, x) => (head.slateX(x + head.width / 2), x + head.width + margin))
}