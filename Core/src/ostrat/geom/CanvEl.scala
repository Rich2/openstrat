/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black
import pDisp._

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait CanvObj[T] extends Any with Transable[T]

/* Base trait for all passive objects  on a canvas / panel */
trait CanvEl[T] extends Any with CanvObj[T]

//case class FillPoly(colour: Colour, verts: Vec2s) extends CanvEl[FillPoly]
//{ override def fTrans(f: Vec2 => Vec2): FillPoly = FillPoly(colour, verts.fTrans(f)) }

/** An efficient FillPoly class based on Array[Double] */
class FillPoly(val arr: Array[Double]) extends AnyVal with CanvEl[FillPoly]
{
   def colour: Colour = Colour(arr(0).toInt)         
   def ptsLength: Int = (arr.length - 1) / 2 
   def xDouble(index: Int): Double = arr(index * 2 + 1)
   def yDouble(index: Int): Double = arr(index * 2 + 2)
   def xHead: Double = arr(1)
   def yHead: Double = arr(2)
   //def setXDouble(index: Int, newValue: Double): Unit = arr(index * 2 + 1) = newValue
   //def setYDouble(index: Int, newValue: Double): Unit = arr(index * 2 + 2) = newValue
   def vert(index: Int): Vec2 = Vec2(xDouble(index), yDouble(index))
  // def setVert(index: Int, newValue: Vec2): Unit = { setXDouble(index, newValue.x); setYDouble(index, newValue.y) }
   def vertsIndexForeach(f: Int => Unit): Unit = (0 until ptsLength).foreach(f)
   def vertsForeach(f: Vec2 => Unit): Unit = vertsIndexForeach(i => f(vert(i)))
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
   
//   {
//      var count = 0
//      while (count < ptsLength) { f(
//   }
   /** Not sure about this name */
   def yVertsArr: Array[Double] =
   {
      val yArr: Array[Double] = new Array[Double](ptsLength)
      vertsIndexForeach{i => yArr(i) = yDouble(i) }      
      yArr
   }
   
   override def fTrans(f: Vec2 => Vec2): FillPoly =
   {
      val newArr: Array[Double] = new Array[Double](arr.length)
      newArr(0) = arr(0)//copies colour      
      var count = 1
      vertsForeach { oldVert =>
         val newVert = f(oldVert)
         newArr(count) = newVert.x
         newArr(count + 1) = newVert.y
         count += 2
      }      
      new FillPoly(newArr)
   }
}

object FillPoly
{
   def apply(colour: Colour, verts: Vec2s): FillPoly =
   {
      val arr: Array[Double] = new Array[Double](1 + verts.arr.length)
      arr(0) = colour.argbValue//copies colour
      verts.arr.copyToRight(arr, 1)
      new FillPoly(arr)
   }
}

case class DrawPoly(verts: Vec2s, lineWidth: Double, lineColour: Colour = Black) extends CanvEl[DrawPoly]
{ override def fTrans(f: Vec2 => Vec2) = DrawPoly(verts.fTrans(f), lineWidth, lineColour) }

case class FillDrawPoly(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends CanvEl[FillDrawPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawPoly(verts.fTrans(f), fillColour, lineWidth, lineColour) }

case class FillShape(segs: Seq[ShapeSeg], fillColour: Colour) extends CanvEl[FillShape]
{ override def fTrans(f: Vec2 => Vec2) = FillShape(segs.fTrans(f), fillColour) }

case class DrawShape(segs: Seq[ShapeSeg], lineWidth: Double, lineColour: Colour = Black) extends CanvEl[DrawShape]
{ override def fTrans(f: Vec2 => Vec2) = DrawShape(segs.fTrans(f), lineWidth, lineColour) }

case class FillDrawShape(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends CanvEl[FillDrawShape]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawShape(segs.fTrans(f), fillColour, lineWidth, lineColour) }

case class DrawArc(arc: Arc, lineWidth: Double, lineColour: Colour = Black) extends CanvEl[DrawArc]
{ override def fTrans(f: Vec2 => Vec2) = DrawArc(arc.fTrans(f), lineWidth, lineColour) }

case class FillText(posn: Vec2, str: String, fontSize: Int, fontColour: Colour = Colour.Black, align: TextAlign = TextCen) extends CanvEl[FillText]
{ override def fTrans(f: Vec2 => Vec2) = FillText(f(posn), str, fontSize, fontColour, align) }
object FillText
{
   def xy(x: Double, y: Double, text: String, fontSize: Int, colour: Colour = Black, align: TextAlign = TextCen) =
      new FillText(Vec2(x, y), text, fontSize, colour, align)
   def lines(posn: Vec2, strs: List[String], fontSize: Int, fontColour: Colour = Colour.Black, lineSpacing: Double = 1.0): List[FillText] =
   {
      val len = strs.length
      ife(len == 0,
            Nil,
            strs.iMap((str, i) => FillText(posn.addY(((len -1) / 2.0 - i) * fontSize * lineSpacing), str, fontSize, fontColour, TextCen))
         )
   }
}
case class FillTextRel(str: String, fontSize: Int, fontColour: Colour = Colour.Black, posn: Vec2 = Vec2Z, align: TextAlign = TextCen) extends
   CanvEl[FillTextRel]{ override def fTrans(f: Vec2 => Vec2) = this }

case class FillDrawTextPoly(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour, str: String, fontSize: Int,
      fontColour: Colour) extends CanvEl[FillDrawTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillDrawTextPoly(verts.fTrans(f), fillColour, lineWidth, lineColour, str, fontSize, fontColour) }

case class FillTextPoly(verts: Vec2s, fillColour: Colour, str: String, fontSize: Int, fontColour: Colour) extends CanvEl[FillTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = FillTextPoly(verts.fTrans(f), fillColour, str, fontSize, fontColour) }

case class DrawTextPoly(verts: Vec2s, lineWidth: Double, lineColour: Colour, str: String, fontSize: Int,
      fontColour: Colour) extends CanvEl[DrawTextPoly]
{ override def fTrans(f: Vec2 => Vec2) = DrawTextPoly(verts.fTrans(f), lineWidth, lineColour, str, fontSize, fontColour) }

case class LineDraw(lineSeg: Line2, lineWidth: Double, linesColour: Colour = Black) extends CanvEl[LineDraw]
{ override def fTrans(f: Vec2 => Vec2) = LineDraw(lineSeg.fTrans(f), lineWidth, linesColour) }

case class PolyLineDraw(lineSegs: Seq[Line2], lineWidth: Double, linesColour: Colour = Black) extends CanvEl[PolyLineDraw]
{ override def fTrans(f: Vec2 => Vec2) = PolyLineDraw(lineSegs.fTrans(f), lineWidth, linesColour) }
