/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import geom._, pCanv._, scalafx.Includes._, scalafx.scene._

case class CanvasFx(canvFx: canvas.Canvas) extends CanvasTopLeft// with CanvSaver
{
  val gc: canvas.GraphicsContext = canvFx.graphicsContext2D
  override def width = canvFx.width.value.max(100)
  override def height = canvFx.height.value.max(100)
  def getButton(e: input.MouseEvent): MouseButton = 
  { import javafx.scene.input.MouseButton._
    e.button.delegate match
    { case PRIMARY => LeftButton
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
  { case 0 => //("scroll delta 0")
    case d if d > 0 => onScroll(true)
    case _ => onScroll(false)
  }

  import paint.Color
  def toFxColor(colour: Colour): paint.Color = Color.rgb(colour.red, colour.green, colour.blue, colour.alpha / 255.0)
  override def tlPolyFill(fp: PolyFill): Unit =
  { gc.fill = toFxColor(fp.colour)
    gc.fillPolygon(fp.xArray, fp.yArray, fp.verts.length)
  }

  /** Needs mod */
  override def tlPolyDraw(dp: PolyDraw): Unit =
  { gc.stroke = toFxColor(dp.colour)
    gc.lineWidth = dp.lineWidth
    gc.strokePolygon(dp.xArray, dp.yArray, dp.vertsLength)
  }
 
  override def tlPolyFillDraw(pfd: PolyFillDraw): Unit =
  { gc.fill = toFxColor(pfd.fillColour)
    gc.fillPolygon(pfd.xArray, pfd.yArray, pfd.vertsLength)
    gc.stroke = toFxColor(pfd.lineColour)
    gc.lineWidth = pfd.lineWidth
    gc.strokePolygon(pfd.xArray, pfd.yArray, pfd.vertsLength)
  }

  override def tlVec2sDraw(pod: Vec2sDraw): Unit =
  { gc.beginPath
    gc.moveTo(pod.xStart, pod.yStart)
    pod.foreachEnd(gc.moveTo)
    gc.stroke = toFxColor(pod.colour)
    gc.lineWidth = pod.lineWidth
    gc.strokePath()
  }
   
  override protected def tlLineDraw(ld: LineDraw): Unit =
  { gc.beginPath
    gc.moveTo(ld.xStart, ld.yStart)
    gc.lineTo(ld.xEnd, ld.yEnd)
    gc.stroke = toFxColor(ld.colour)
    gc.lineWidth = ld.lineWidth
    gc.stroke()
  }
   
  override protected def tlArcDraw(ad: ArcDraw): Unit =
  { gc.beginPath
    gc.moveTo(ad.xStart, ad.yStart)
    ad.fControlEndRadius(gc.arcTo)
    gc.stroke = toFxColor(ad.colour)
    gc.stroke()
  }
   
  def fxAlign(align: TextAlign) =
  { import javafx.scene.text._
    align match
    { case TextCen => TextAlignment.CENTER
      case TextLeft => TextAlignment.LEFT
      case TextRight => TextAlignment.RIGHT
    }
  }
   
  protected def tlBezierDraw(bd: BezierDraw): Unit =
  { gc.stroke = toFxColor(bd.colour)
    gc.lineWidth = bd.lineWidth
    gc.beginPath
    gc.moveTo(bd.xStart, bd.yStart)
    gc.bezierCurveTo(bd.xC1, bd.yC1, bd.xC2, bd.yC2, bd.xEnd, bd.yEnd)
    gc.stroke()
  }
   
  override def tlTextGraphic(tg: TextGraphic): Unit =
  { gc.setTextAlign(fxAlign(tg.align))
    gc.setTextBaseline(javafx.geometry.VPos.CENTER)
    gc.setFont(new text.Font(tg.fontSize))
    gc.fill = toFxColor(tg.colour)
    gc.fillText(tg.str, tg.posn.x, tg.posn.y)
  }
   
  protected def tlLinesDraw(lsd: LinesDraw): Unit =
  { gc.beginPath
    lsd.lineSegs.foreach(ls => { gc.moveTo(ls.xStart, ls.yStart);  gc.lineTo(ls.xEnd, ls.yEnd)})
    gc.lineWidth = lsd.lineWidth
    gc.stroke = toFxColor(lsd.colour)
    gc.stroke()
  }
   
  override def tlTextOutline(to: TextOutline): Unit =
  { gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER)
    gc.setTextBaseline(javafx.geometry.VPos.CENTER)
    gc.stroke = toFxColor(to.colour)
    gc.lineWidth = 1
    gc.setFont(new text.Font(to.fontSize))
    gc.strokeText(to.str, to.posn.x, to.posn.y)
  }
   
  private def segsPath(segs: Shape): Unit =
  { gc.beginPath
    var startPt = segs.last.pEnd
    gc.moveTo(startPt.x, startPt.y)
    segs.foreach{seg =>
      seg.segDo(ls => gc.lineTo(ls.xEnd, ls.yEnd),
        as => as.fControlEndRadius(startPt, gc.arcTo),
        bs => gc.bezierCurveTo(bs.xC1, bs.yC1, bs.xUses, bs.yUses, bs.xEnd, bs.yEnd)
      )
      startPt = seg.pEnd
    }
    gc.closePath
  }
   
  override def tlShapeFill(sf: ShapeFill): Unit =
  { segsPath(sf.shape)
    gc.fill = toFxColor(sf.colour)
    gc.fill()
  }
   
  override def tlShapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =
  { segsPath(segs)
    gc.fill = toFxColor(fillColour)
    gc.fill()
    gc.lineWidth = lineWidth
    gc.stroke = toFxColor(lineColour)
    gc.stroke()
  }

  override def tlShapeDraw(sd: ShapeDraw): Unit =
  { segsPath(sd.segs)
    gc.lineWidth = sd.lineWidth
    gc.stroke = toFxColor(sd.colour)
    gc.stroke()
  }

  override def clear(colour: Colour): Unit =
  { gc.fill = toFxColor(colour)
    gc.fillRect(0, 0, width, height)
  }

  def getTime: Double = System.currentTimeMillis
  import javafx.animation._
  override def timeOut(f: () => Unit, millis: Integer): Unit = new Timeline(new KeyFrame(javafx.util.Duration.millis(millis.doubleValue()),
    (ae: scalafx.event.ActionEvent) => f())).play
   
  override def tlClip(pts: Polygon): Unit =
  { gc.beginPath
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
