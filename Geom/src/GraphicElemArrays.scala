/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Not sure how useful these classes are in the immediate as when used in a collection they will box, undermining the benefits of using an Array */
trait ValsVec2s extends Any
{
   def arr: Array[Double]
   def valsLength: Int
   def ptsLength: Int = (arr.length - valsLength) / 2   
   def xDouble(index: Int): Double = arr(index * 2 + valsLength)
   def yDouble(index: Int): Double = arr(index * 2 + valsLength + 1)
   def xHead: Double = arr(valsLength)
   def yHead: Double = arr(valsLength + 1)   
   def vert(index: Int): Pt2 = Pt2(xDouble(index), yDouble(index))
   def vertsIndexForeach(f: Int => Unit): Unit = (0 until ptsLength).foreach(f)
   def vertsForeach(f: Pt2 => Unit): Unit = vertsIndexForeach(i => f(vert(i)))
   def arrTrans(f: Pt2 => Pt2): Array[Double]
   def foreachVertPairTail[U](f: (Double, Double) => U): Unit =
   {
      var count = 1      
      while(count < ptsLength) { f(xDouble(count), yDouble(count)); count += 1 }
   }
   
   /** Not sure about this name */
   def xVertsArr: Array[Double] =
   {
      val xArr: Array[Double] = new Array[Double](ptsLength)
      vertsIndexForeach{i => xArr(i) = xDouble(i) }      
      xArr
   }   

   /** Not sure about this name */
   def yVertsArr: Array[Double] =
   {
      val yArr: Array[Double] = new Array[Double](ptsLength)
      vertsIndexForeach{i => yArr(i) = yDouble(i) } //think this is wrong     
      yArr
   }
   def arrVertsTrans(f: Pt2 => Pt2): Array[Double] =
   {
      val newArr: Array[Double] = new Array[Double](arr.length)
      var count = valsLength
      vertsForeach { oldVert =>
         val newVert = f(oldVert)
         newArr(count) = newVert.x
         newArr(count + 1) = newVert.y
         count += 2
      }
      newArr
   }
}

trait Val1Vec2s[Val1T] extends Any with ValsVec2s
{
   def valsLength: Int = 1
   def val1Func: Double => Val1T
   def val1: Val1T = val1Func(arr(0))   
   
   def arrTrans(f: Pt2 => Pt2): Array[Double] =
   {
      val newArr: Array[Double] = arrVertsTrans(f)
      newArr(0) = arr(0)//copies initial Val1 value      
      newArr
   }   
}

trait Val2Vec2s[Val1T, Val2T] extends Any with ValsVec2s
{
   override def valsLength: Int = 2
   def val1Func: Double => Val1T
   def val1: Val1T = val1Func(arr(0))
   def val2Func: Double => Val2T
   def val2: Val2T = val2Func(arr(1))
   override def arrTrans(f: Pt2 => Pt2): Array[Double] =
   {
      val newArr: Array[Double] = arrVertsTrans(f)//new Array[Double](arr.length)
      newArr(0) = arr(0)//copies initial Val1 value      
      newArr(1) = arr(1)//copies initial Val2 value 
      newArr
   }      
}

trait Val3Vec2s[Val1T, Val2T, Val3T] extends Any with ValsVec2s
{
   override def valsLength: Int = 3
   def val1Func: Double => Val1T
   def val1: Val1T = val1Func(arr(0))
   def val2Func: Double => Val2T
   def val2: Val2T = val2Func(arr(1))
   def val3Func: Double => Val3T
   def val3: Val3T = val3Func(arr(2))
   override def arrTrans(f: Pt2 => Pt2): Array[Double] =
   {
      val newArr: Array[Double] = arrVertsTrans(f)//new Array[Double](arr.length)
      newArr(0) = arr(0)//copies initial Val1 value      
      newArr(1) = arr(1)//copies initial Val2 value
      newArr(2) = arr(2)//copies initial Vat3 value
      newArr
   }      
}

/** An attempt at an efficient class to fill polygon based on Array[Double]. These classes are currently implemented as Array[Double]s. But this is
 *   completely pointless, as they will never be in collections such as List[FillPoly] or List[FillDraw], so they will always need to be boxed. The
 *    lesson is don't try and make value classes out of indeterminate length objects. See #21 */
//class FillPolyAlt(val arr: Array[Double]) extends AnyVal with Val1Vec2s[Colour] with PaintElem[FillPolyAlt]
//{ 
//   override def val1Func: Double => Colour = d => Colour(d.toInt)
//   @inline def colour: Colour = val1
//   override def fTrans(f: Vec2 => Vec2): FillPolyAlt =  new FillPolyAlt(arrTrans(f))
//}
//
//object FillPolyAlt
//{
//   def apply(colour: Colour, verts: Vec2s): FillPolyAlt =
//   {
//      val arr: Array[Double] = new Array[Double](1 + verts.arr.length)
//      arr(0) = colour.argbValue//copies colour
//      verts.arr.copyToRight(arr, 1)
//      new FillPolyAlt(arr)
//   }
//}
//
///** An attempt at an efficient class to fill polygon based on Array[Double]. These classes are currently implemented as Array[Double]s. But this is
// *   completely pointless, as they will never be in collections such as List[FillPoly] or List[FillDraw], so they will always need to be boxed. The
// *   lesson is don't try and make value classes out of indeterminate length objects. See #21 */
//class DrawPolyAlt(val arr: Array[Double]) extends AnyVal with Val2Vec2s[Double, Colour] with PaintElem[DrawPolyAlt]
//{
//   override def toString = "DrawPoly" - arr.map(_.toString).commaFold.enParenth
//   override def val1Func: Double => Double = d => d
//   @inline def lineWidth: Double = val1
//   override def val2Func: Double => Colour = d => Colour(d.toInt)
//   @inline def lineColour: Colour = val2
//   override def fTrans(f: Vec2 => Vec2) = new DrawPolyAlt(arrTrans(f))   
//}
//
//object DrawPolyAlt
//{
//   def apply(lineWidth: Double, colour: Colour, verts: Vec2s): DrawPolyAlt =
//   {
//      val arr: Array[Double] = new Array[Double](2 + verts.arr.length)
//      arr(0) = lineWidth
//      arr(1) = colour.argbValue//copies colour
//      verts.arr.copyToRight(arr, 2)
//      new DrawPolyAlt(arr)
//   }
//}
