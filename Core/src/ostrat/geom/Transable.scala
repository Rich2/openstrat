/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T. */
trait Transable[T] extends Any
{
   def fTrans(f: Vec2 => Vec2):  T
   /** Translate in 2 dimensional space */
   def slate(offset: Vec2): T = fTrans(_ + offset)
   def slate(xOffset: Double, yOffset: Double): T = fTrans(_.addXY(xOffset, yOffset))
   def slateX(xOffset: Double): T = fTrans(_.addX(xOffset))
   def slateY(yOffset: Double): T = fTrans(_.addY(yOffset))
   def scale(factor: Double): T = fTrans(_ * factor)

   def rotate(angle: Angle): T = fTrans(_.rotate(angle))
   def rotateRadians(r: Double): T = fTrans(_.rotateRadians(r))
   def scaleY(factor: Double): T = fTrans(_.scaleY(factor))
   /** this.asInstanceOf[T] */  
   def identity: T = this.asInstanceOf[T]   
   def mirrorX: T = fTrans(_.mirrorX)
   def mirrorY: T = fTrans(_.mirrorY)
   def mirror4: List[T] = List(fTrans(v => v), fTrans(_.mirrorX), fTrans(_.mirrorY), fTrans(- _))
   def withNegate: Seq[T] = Seq(identity, fTrans(- _))
   def inverseY: T = fTrans(v => Vec2(v.x, -v.y))
   
   import math.Pi
   /** Rotates 30 degrees anti-clockwise or + Pi/3 */
   def p30: T = rotate(Angle(Pi / 6))
   /** Rotates 45 degrees anti-clockwise or + Pi/4 */
   def p45: T = rotate(Angle(Pi / 4))
   /** Rotates 60 degrees anti-clockwise or + Pi/3 */
   def p60: T  = rotate(Angle(Pi / 3))  
   /** Rotates 90 degrees anti-clockwise or + Pi/2 */
   def p90: T = rotate(Angle(Pi / 2))  
   /** Rotates 120 degrees anti-clockwise or + 2 * Pi/3 */
   def p120: T = rotate(Angle(2 * Pi / 3))
   /** Rotates 135 degrees anti-clockwise or + 3 * Pi/4 */
   def p135: T = rotate(Angle(3 * Pi / 4))
   /** Rotates 150 degrees anti-clockwise or + 5 * Pi/6 */
   def p150: T = rotate(Angle(5 * Pi / 6))
   
   /** Rotates 30 degrees clockwise or - Pi/3 */
   def m30: T = rotate(Angle(-Pi / 6))
   /** Rotates 45 degrees clockwise or - Pi/4 */
   def m45: T = rotate(Angle(-Pi / 4))
   /** Rotates 60 degrees clockwise or - Pi/3 */
   def m60: T  = rotate(Angle(-Pi / 3))  
   /** Rotates 90 degrees clockwise or - Pi / 2 */
   def m90: T = rotate(Angle(-Pi / 2))  
   /** Rotates 120 degrees clockwise or - 2 * Pi/3 */
   def m120: T = rotate(Angle(-2 * Pi / 3))
   /** Rotates 135 degrees clockwise or - 3 * Pi/ 4 */
   def m135: T = rotate(Angle(-3 * Pi / 4))
   /** Rotates 150 degrees clockwise or - 5 * Pi/ 6 */
   def m150: T = rotate(Angle(-5 * Pi / 6))
   
   /** Produces a regular cross of a sequence of four of the elements rotated */
   def rCross: Seq[T] = (1 to 4).map(i => rotate(deg90 * i))
}

object Transable
{
//   import scala.collection.mutable.{ Builder }
//   import scala.collection._
   implicit class ImplictTransableList[TT <: Transable[_ ]](tList: List[TT]) extends Transable[List[TT]]
   {
      def fTrans(f: Vec2 => Vec2): List[TT] = tList.map(_.fTrans(f).asInstanceOf[TT])      
      def flatMirror4: List[TT] = tList.flatMap(_.mirror4.asInstanceOf[Seq[TT]])      
      //def flatWithNegate: List[TT] = tList.flatMap(_.withNegate.asInstanceOf[Seq[TT]])
   }
   
//   implicit class ImplicitTransableTrav[TransT <: Transable[TransT], Repr](travLike: TraversableLike[TransT, Repr])// extends Traversable[Repr]
//   {
//      
//      def fTrans(f: Vec2 => Vec2)(implicit bf: generic.CanBuildFrom[Repr, TransT, Repr]): Repr =
//      {
//         def builder: Builder[TransT,Repr] =
//         { // extracted to keep method size under 35 bytes, so that it can be JIT-inlined
//            val b = bf(travLike.repr)
//            b.sizeHint(travLike)
//            b
//         }
//         val b = builder
//         for (transable <- travLike) b += transable.fTrans(f)
//         b.result
//      }
//      def slate (offset: Vec2)(implicit bf: generic.CanBuildFrom[Repr, TransT, Repr]): Repr =
//      {
//         def builder: Builder[TransT, Repr] =
//         { // extracted to keep method size under 35 bytes, so that it can be JIT-inlined
//            val b = bf(travLike.repr)
//            b.sizeHint(travLike)
//            b
//         }
//         val b = builder
//         for (transable <- travLike) b += transable.slate(offset)
//         b.result
//      }
//      def slate(xOff: Double, yOff: Double)(implicit bf: generic.CanBuildFrom[Repr, TransT, Repr]): Repr =
//      {
//         def builder =
//         { // extracted to keep method size under 35 bytes, so that it can be JIT-inlined
//            val b = bf(travLike.repr)
//            b.sizeHint(travLike)
//            b
//         }
//         val b = builder
//         for (transable <- travLike) b += transable.slate(xOff, yOff)
//         b.result
//      }
//      def scale(factor: Double)(implicit bf: generic.CanBuildFrom[Repr, TransT, Repr]): Repr =
//      {
//         def builder =
//         { // extracted to keep method size under 35 bytes, so that it can be JIT-inlined
//            val b = bf(travLike.repr)
//            b.sizeHint(travLike)
//            b
//         }
//         val b = builder
//         for (transable <- travLike) b += transable.scale(factor)
//         b.result
//      }
//   }
}