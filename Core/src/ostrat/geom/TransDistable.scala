/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransDistable[T] extends Any
{
   def fTrans(f: Dist2 => Dist2):  T  
   def slate(offset: Dist2): T = fTrans(_ + offset)
   def slate(xOffset: Dist, yOffset: Dist): T = fTrans(_.addXY(xOffset, yOffset))
   def slateX(xOffset: Dist): T = fTrans(_.addX(xOffset))
   def slateY(yOffset: Dist): T = fTrans(_.addY(yOffset))
   def scale(factor: Double): T = fTrans(_ * factor)

   def rotate(angle: Angle): T = fTrans(_.rotate(angle))
   def rotateRadians(r: Double): T = fTrans(_.rotateRadians(r))
   //def scaleY(factor: Double): T = fTrans(_.scaleY(factor))
   /** this.asInstanceOf[T] */  
   def identity: T = this.asInstanceOf[T]   
   //def mirrorX: T = fTrans(_.mirrorX)
   //def mirrorY: T = fTrans(_.mirrorY)
   //def mirror4: List[T] = List(fTrans(v => v), fTrans(_.mirrorX), fTrans(_.mirrorY), fTrans(- _))
   //def withNegate: Seq[T] = Seq(identity, fTrans(- _))
   def inverseY: T = fTrans(v => Dist2(v.x, -v.y))  
}