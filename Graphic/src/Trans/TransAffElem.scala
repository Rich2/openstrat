/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An object that can transform itself in 2d geometry and can preserve its type across all affine transofrmations. This is a key trait, the object
 *  can be transformed in 2 dimensional space. Leaf classes mustimplement the single method fTrans(f: Vec2 => Vec2): T. The related trait
 *  TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait TransAffElem extends TransSimElem
{ type ThisT <: TransAffElem  

  /** A generalised shear transformation. I think this is correct. */
 // def shear(xScale: Double, yScale: Double): AffineElem = ??? // fTrans(v => v.x * yScale vv v.y * xScale)

  
  def rotateRadians(radians: Double): ThisT = fTrans(_.rotateRadians(radians))
  def mirror(v1: Vec2, v2: Vec2): ThisT = fTrans(_.mirror(v1, v2))

  override def mirror(line: Line2): ThisT = fTrans((_.mirror(line)))

  

  override def prolign(matrix: ProlignMatrix): ThisT = fTrans(_.prolignTrans(matrix))

  

  override def scaleXY(xOperand: Double, yOperand: Double): ThisT = ???
}