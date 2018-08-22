/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import geom._
import pCanv._
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
         case 0 => //("scroll delta 0")
         case d if d > 0 => onScroll(true)
         case _ => onScroll(false)
      }
   import paint.Color   
   def fxColor(colour: Colour): paint.Color = Color.rgb(colour.red, colour.green, colour.blue, colour.alpha / 255.0)
   override def tlPolyFill(fp: PolyFill): Unit =
   {
      gc.fill = fxColor(fp.colour)
      gc.fillPolygon(fp.xArray, fp.yArray, fp.verts.length)
   }

   /** Needs mod */
   override def tlPolyDraw(dp: PolyDraw): Unit =    
   {
      gc.stroke = fxColor(dp.lineColour)
      gc.lineWidth = dp.lineWidth
      gc.strokePolygon(dp.xArray, dp.yArray, dp.vertsLength)  
   }
 
   override def tlPolyFillDraw(pfd: PolyFillDraw): Unit =    
   {      
      gc.fill = fxColor(pfd.fillColour)           
      gc.fillPolygon(pfd.xArray, pfd.yArray, pfd.vertsLength)
      gc.stroke = fxColor(pfd.lineColour)
      gc.lineWidth = pfd.lineWidth
      gc.strokePolygon(pfd.xArray, pfd.yArray, pfd.vertsLength)  
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
   
   protected def tlBezierDraw(bd: BezierDraw): Unit =
   {
      deb("Starting draw bezier")
      
     // gc.stroke = fxColor(Colour.Black)//bd.colour)
      
     // gc.lineWidth = 15//bd.lineWidth
      gc.beginPath
      gc.moveTo(75, 200)//(bd.xStart, bd.yStart)
      gc.lineTo(400, 450)
      gc.lineTo(300, 600)
      //gc.bezierCurveTo(200, 50, 300, 50, 525, 200)//(bd.xC1, bd.yC1, bd.xC2, bd.yC2, bd.xEnd, bd.yEnd)
     gc.closePath() 
     gc.stroke = fxColor(Colour.Red)//bd.colour)
      
      gc.lineWidth = 2//bd.lineWidth
      gc.stroke
   }   
   
   override def tlTextGraphic(x: Double, y: Double, str: String, fontSize: Int, textColour: Colour, align: TextAlign) = 
   {
      gc.setTextAlign(fxAlign(align))
      gc.setTextBaseline(javafx.geometry.VPos.CENTER)
      gc.setFont(new text.Font(fontSize))
      gc.fill = fxColor(textColour)      
      gc.fillText(str, x, y)
   }
   
   protected def tlLinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit =
   {           
      gc.beginPath
      lineSegs.foreach(ls => { gc.moveTo(ls.x1, ls.y1);  gc.lineTo(ls.x2, ls.y2)})
      gc.lineWidth = lineWidth
      gc.stroke = fxColor(linesColour)
      gc.stroke()      
   }
   
   override def tlTextDraw(x: Double, y: Double, str: String, fontSize: Int, lineColour: Colour) = 
   {
      gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER)
      gc.setTextBaseline(javafx.geometry.VPos.CENTER)
      gc.stroke = fxColor(lineColour)
      gc.lineWidth = 1
      gc.setFont(new text.Font(fontSize))      
      gc.strokeText(str, x, y)      
   }
   
   private def segsPath(segs: List[ShapeSeg]): Unit =
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
            }
            p1 = s.endPt 
         })
      gc.closePath 
   }
   
   override def tlShapeFill(segs: List[ShapeSeg], fillColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fill = fxColor(fillColour)
      gc.fill()      
   }
   
   override def tlShapeFillDraw(segs: List[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fill = fxColor(fillColour)
      gc.fill()
      gc.stroke = fxColor(lineColour)
      gc.stroke()
   }   
   override def tlShapeDraw(segs: List[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.stroke = fxColor(lineColour)
      gc.stroke()      
   }
   
   override def tlArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit =
   {      
      gc.beginPath
      gc.moveTo(arc.startPt.x, arc.startPt.y)
      arc.fArcTo(gc.arcTo)
      gc.stroke = fxColor(lineColour)
      gc.stroke()
   }
   
   override def clear(colour: Colour): Unit =
   {
      gc.fill = fxColor(colour)
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
   def saveFile(fileName: String, output: String): Unit = saveRsonFile(openStratDir, fileName, output: String)
   def loadFile(fileName: String): EMon[String] = loadRsonFile(openStratDir / fileName)   
}
