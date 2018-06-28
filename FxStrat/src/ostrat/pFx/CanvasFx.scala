/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import geom._
import pDisp._
import scalafx.Includes._
import scalafx.scene._

case class CanvasFx(canvFx: canvas.Canvas) extends CanvasTopLeft// with CanvSaver
{
   val gc: canvas.GraphicsContext = canvFx.graphicsContext2D
   override def width = canvFx.width.value.max(100)
   override def height = canvFx.height.value.max(100)
   def getButton(e: input.MouseEvent): MouseButton = 
   {
      import javafx.scene.input.MouseButton._
      e.button.delegate match
      {
         case PRIMARY => LeftButton
         case MIDDLE => MiddleButton
         case SECONDARY => RightButton
         case _ => NoButton
      }     
   }
   canvFx.onMouseReleased = (e: input.MouseEvent) => mouseUpTopLeft(e.x, e.y, getButton(e))   
   //canvFx.onMousePressed = (e: input.MouseEvent) => mouseDownTopLeft(e.x, e.y, getButton(e))   
   //canvFx.onMouseMoved = (e: input.MouseEvent) => mouseMovedTopLeft(e.x, e.y, getButton(e))    
   canvFx.onMouseDragged = (e: input.MouseEvent) => mouseDraggedTopLeft(e.x, e.y, getButton(e))   
   
   canvFx.onScroll = (e: input.ScrollEvent) => e.deltaY match
      {
         case 0 => println("scroll delta 0")
         case d if d > 0 => onScroll(true)
         case _ => onScroll(false)
      }
   import paint.Color   
   def col(colour: Colour): Color = Color.rgb(colour.red, colour.green, colour.blue, colour.alpha / 255.0)
   override def tlPolyFill(verts: Vec2s, colour: Colour): Unit =
   {
      gc.fill = col(colour)
      gc.fillPolygon(verts.elem1sArray, verts.elem2sArray, verts.length)
   }

   override def tlPolyDraw(verts: Vec2s, lineWidth: Double, lineColour: Colour): Unit =    
  {      
     gc.stroke = col(lineColour)
     gc.lineWidth = lineWidth
     gc.strokePolygon(verts.elem1sArray, verts.elem2sArray, verts.length)  
   }
 
   override def tlPolyFillDraw(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =    
   {      
      gc.fill = col(fillColour)           
      gc.fillPolygon(verts.elem1sArray, verts.elem2sArray, verts.length)
      gc.stroke = col(lineColour)
      gc.lineWidth = lineWidth
      gc.strokePolygon(verts.elem1sArray, verts.elem2sArray, verts.length)  
   }
   
   def fxAlign(align: TextAlign) =
   {
      import javafx.scene.text._
      align match
   {
      case TextCen => TextAlignment.CENTER
      case TextLeft => TextAlignment.LEFT
      case TextRight => TextAlignment.RIGHT
   }
   }
   override def tlTextFill(x: Double, y: Double, str: String, fontSize: Int, textColour: Colour, align: TextAlign) = 
   {
      gc.setTextAlign(fxAlign(align))
      gc.setTextBaseline(javafx.geometry.VPos.CENTER)
      gc.setFont(new text.Font(fontSize))
      gc.fill = col(textColour)      
      gc.fillText(str, x, y)
   }
   
   protected def tlLineSegsDraw(lineSegs: Seq[Line2], lineWidth: Double, linesColour: Colour): Unit =
   {           
      gc.beginPath
      lineSegs.foreach(ls => { gc.moveTo(ls.x1, ls.y1);  gc.lineTo(ls.x2, ls.y2)})
      gc.lineWidth = lineWidth
      gc.stroke = col(linesColour)
      gc.stroke()      
   }
   
   override def tlTextDraw(x: Double, y: Double, str: String, fontSize: Int, lineColour: Colour) = 
   {
      gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER)
      gc.setTextBaseline(javafx.geometry.VPos.CENTER)
      gc.stroke = col(lineColour)
      gc.lineWidth = 1
      gc.setFont(new text.Font(fontSize))      
      gc.strokeText(str, x, y)      
   }
   
   private def segsPath(segs: Seq[ShapeSeg]): Unit =
   {
      gc.beginPath      
      var p1 = segs.last.endPt
      gc.moveTo(p1.x, p1.y)
      segs.foreach(s =>
         {
            s match
            {
               case LineSeg(pt2) => gc.lineTo(pt2.x, pt2.y)
               case as: ArcSeg => as.fArcTo(p1, gc.arcTo)
//                  {
//                     val radius: Double = as.radius
//                     val endPt = as.endPt                     
//                     //gc.arcTo(ctlPt.x, ctlPt.y, endPt.x, endPt.y, radius)
//                     //This is incorrect
//                     gc.arcTo(p1.x, p1.y, endPt.x, endPt.y, radius)
//                  }
            }
            p1 = s.endPt 
         })
      gc.closePath 
   }
   
   override def tlShapeFill(segs: Seq[ShapeSeg], fillColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fill = col(fillColour)
      gc.fill()      
   }
   
   override def tlShapeFillDraw(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fill = col(fillColour)
      gc.fill()
      gc.stroke = col(lineColour)
      gc.stroke()
   }   
   override def tlShapeDraw(segs: Seq[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.stroke = col(lineColour)
      gc.stroke()      
   }
   
   override def tlArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit =
   {
      println(arc)
      gc.beginPath
      gc.moveTo(arc.startPt.x, arc.startPt.y)
      arc.fArcTo(gc.arcTo)
      gc.stroke = col(lineColour)
      gc.stroke()
   }
   
   override def clear(colour: Colour): Unit =
   {
      gc.fill = col(colour)
      gc.fillRect(0, 0, width, height)
   }  
   def getTime: Double = System.currentTimeMillis
   import javafx.animation._
   override def timeOut(f: () => Unit, millis: Integer): Unit = new Timeline(new KeyFrame(javafx.util.Duration.millis(millis.doubleValue()),
         (ae: scalafx.event.ActionEvent) => f())).play
   
   override def tlClip(pts: Vec2s): Unit =
   {
      gc.beginPath      
      gc.moveTo(pts.head1, pts.head2)
      pts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.clip()
   }      
   override def gcSave(): Unit = gc.save()
   override def gcRestore(): Unit = gc.restore() 
   val saveDirStr =  System.getProperty("user.home") / "AppData/Local/OpenStratData"
   def saveFile(fileName: String, output: String): Unit =
   {
      import java.io._
      val dir = new File(saveDirStr)
      if (!dir.exists) dir.mkdirs
      val pw = new PrintWriter(new File(saveDirStr / fileName))
      pw.write(output)
      pw.close
   }
  def loadFile(fileName: String): EMon[String] = eTry(io.Source.fromFile(saveDirStr / fileName).mkString)
   
}
