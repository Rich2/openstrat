/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pDisp

trait CanvasTopLeftStubs extends CanvasTopLeft
{
   override def clip(pts: Vec2s): Unit = {}
   override def getTime: Double = 0
   override protected def tlPolyFill(pts: Vec2s, colour: Colour): Unit = {}
   override protected def tlPolyDraw(pts: Vec2s, lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlPolyFillDraw(pts: Vec2s, colour: Colour, lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlLineSegsDraw(lineSegs: Seq[Line2], lineWidth: Double, linesColour: Colour): Unit = {}

   override protected def tlShapeFill(segs: Seq[ShapeSeg], colour: Colour): Unit = {}
   override protected def tlShapeFillDraw(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlShapeDraw(segs: Seq[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit = {}
   override protected def tlArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit = {}
   
   override protected def tlTextFill(x: Double, y: Double, text: String, fontSize: Int, textColour: Colour, align: TextAlign): Unit = {}
   override protected def tlTextDraw(x: Double, y: Double, text: String, fontSize: Int, lineColour: Colour): Unit = {}
//   override protected def tlCircleFill(x: Double, y: Double, radius: Double, colour: Colour): Unit = {}
   //override protected def mouseUpTopLeft(x: Double, y: Double, mb: MouseButton): Unit = mouseUp(Vec2(x - width / 2, height / 2 - y), mb)
   
   override protected def tlClip(pts: Vec2s): Unit = {}
   def clear(colour: Colour = Colour.White): Unit = {} 
   def gcSave(): Unit = {} 
   def gcRestore(): Unit = {}
   def timeOut(f: () => Unit, millis: Integer): Unit = {}
}