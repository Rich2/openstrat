/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import geom._, pCanv._, javafx._, scene._

/** An alternative version of CanvasFx to experiment with removing the ScalaFx dependency and just use JavaFx directly. */
case class CanvasFx(canvFx: canvas.Canvas, theScene: Scene) extends CanvasTopLeft// with CanvSaver
{
  val gc: canvas.GraphicsContext = canvFx.getGraphicsContext2D
  override def width = canvFx.getWidth.max(100)
  override def height = canvFx.getHeight.max(100)
  def getButton(e: input.MouseEvent): MouseButton = 
  { import input.MouseButton._
    e.getButton match
    { case PRIMARY => LeftButton
      case MIDDLE => MiddleButton
      case SECONDARY => RightButton
      case _ => NoButton
    }     
  }
  //I think getX is wanted not getSceneX or getScreenX
  canvFx.setOnMouseReleased((e: input.MouseEvent) => mouseUpTopLeft(e.getX(), e.getY, getButton(e)))   
  //canvFx.onMousePressed = (e: input.MouseEvent) => mouseDownTopLeft(e.x, e.y, getButton(e))   
  //canvFx.onMouseMoved = (e: input.MouseEvent) => mouseMovedTopLeft(e.x, e.y, getButton(e))    
  canvFx.setOnMouseDragged((e: input.MouseEvent) => mouseDraggedTopLeft(e.getX, e.getY, getButton(e))) 
  
  theScene.setOnKeyReleased{(e: input.KeyEvent) => keyUp(e.getText) }
  theScene.setOnKeyPressed{(e: input.KeyEvent) => keyDown(e.getText) }

  canvFx.setOnScroll((e: input.ScrollEvent) => e.getDeltaY match
  { case 0 => //("scroll delta 0")
    case d if d > 0 => onScroll(true)
    case _ => onScroll(false)
  })

  import paint.Color
  def toFxColor(colour: Colour): Color = Color.rgb(colour.red, colour.green, colour.blue, colour.alpha / 255.0)
  override def tlPolyFill(poly: Polygon, colour: Colour): Unit =
  { gc.setFill(toFxColor(colour))
    gc.fillPolygon(poly.elem1sArray, poly.elem2sArray, poly.length)
  }

  /** Needs mod */
  override protected[this] def tlPolyDraw(poly: Polygon, lineWidth: Double, colour: Colour): Unit =
  { gc.setStroke(toFxColor(colour))
    gc.setLineWidth(lineWidth)
    gc.strokePolygon(poly.elem1sArray, poly.elem2sArray, poly.length)
  }

  override protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit =
  { gc.beginPath
    gc.moveTo(pod.xStart, pod.yStart)
    pod.foreachEnd(gc.lineTo)
    gc.setStroke(toFxColor(pod.colour))
    gc.setLineWidth(pod.lineWidth)
    gc.stroke()
  }
   
  override protected[this] def tlLineDraw(ld: LineDraw): Unit =
  { gc.beginPath
    gc.moveTo(ld.xStart, ld.yStart)
    gc.lineTo(ld.xEnd, ld.yEnd)
    gc.setStroke(toFxColor(ld.colour))
    gc.setLineWidth(ld.width)
    gc.stroke()
  }
   
  override protected[this] def tlCArcDrawOld(ad: CArcDrawOld): Unit =
  { gc.beginPath
    gc.moveTo(ad.xStart, ad.yStart)
    ad.fControlEndRadius(gc.arcTo)
    gc.setStroke(toFxColor(ad.colour))
    gc.stroke()
  }
  override def tlCircleFill(cf: CircleFill): Unit =
  { val circ = cf.circle
    gc.setFill(toFxColor(cf.colour))
    debvar(circ)
    gc.fillOval(circ.x, circ.y, circ.radius, circ.radius)
  }
  override protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit =
  { gc.beginPath
    gc.moveTo(dld.xStart, dld.yStart)
    gc.lineTo(dld.xEnd, dld.yEnd)
    gc.setStroke(toFxColor(dld.colour))
    gc.setLineWidth(dld.lineWidth)
    gc.setLineDashes(dld.dashArr :_*)
    gc.stroke()
    gc.setLineDashes()
  }

  override protected[this] def tlCArcDraw(cad: CArcDraw): Unit =
  { val ca = cad.arc
    gc.beginPath
    gc.moveTo(ca.xStart, ca.yStart)
    gc.arcTo(ca.xCtrl, ca.yCtrl, ca.xEnd, ca.yEnd, ca.radius)
    //gc.arc(ca.xCen, ca.yCen, ca.radius, ca.radius, ca.startAngleRadians.radiansToDegrees, ca.detltaDegs)
    gc.setStroke(toFxColor(cad.colour))
    gc.stroke()
  }
  def fxAlign(align: TextAlign) =
  { import text._
    align match
    { case CenAlign => TextAlignment.CENTER
      case LeftAlign => TextAlignment.LEFT
      case RightAlign => TextAlignment.RIGHT
    }
  }

  def fxBaseline(baseLine: BaseLine) =
  {
    import BaseLine._
    baseLine match
    { case Top => geometry.VPos.TOP
      case Middle => geometry.VPos.CENTER
      case Alphabetic => geometry.VPos.BASELINE
      case Bottom => geometry.VPos.BOTTOM
      case Hanging => geometry.VPos.TOP
      case Ideographic => geometry.VPos.BASELINE
    }
  }

  override protected[this] def tlBezierDraw(bd: BezierDraw): Unit =
  { gc.setStroke(toFxColor(bd.colour))
    gc.setLineWidth(bd.lineWidth)
    gc.beginPath
    gc.moveTo(bd.xStart, bd.yStart)
    gc.bezierCurveTo(bd.xC1, bd.yC1, bd.xC2, bd.yC2, bd.xEnd, bd.yEnd)
    gc.stroke()
  }
   
// baseline - VPos with values of Top, Center, Baseline, or Bottom or null.

  override protected[this] def tlTextGraphic(tg: TextGraphic): Unit =
  { gc.setTextAlign(fxAlign(tg.align))
    gc.setTextBaseline(fxBaseline(tg.baseLine))
    gc.setFont(new text.Font(tg.fontSize))
    gc.setFill(toFxColor(tg.colour))
    gc.fillText(tg.str, tg.posn.x, tg.posn.y)
  }
   
  protected[this] def tlLinesDraw(lsd: LinesDraw): Unit =
  { gc.beginPath
    lsd.lines.foreach(ls => { gc.moveTo(ls.xStart, ls.yStart);  gc.lineTo(ls.xEnd, ls.yEnd)})
    gc.setLineWidth(lsd.lineWidth)
    gc.setStroke(toFxColor(lsd.colour))
    gc.stroke()
  }
   
  override protected[this] def tlTextOutline(to: TextOutline): Unit =
  { gc.setTextAlign(text.TextAlignment.CENTER)
    gc.setTextBaseline(fxBaseline(to.baseLine))
    gc.setStroke(toFxColor(to.colour))
    gc.setLineWidth(1)
    gc.setFont(new text.Font(to.fontSize))
    gc.strokeText(to.str, to.posn.x, to.posn.y)
  }
   
  private[this] def segsPath(segs: PolyCurve): Unit =
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
   
  override protected[this] def tlShapeFill(shape: PolyCurve, colour: Colour): Unit =
  { segsPath(shape)
    gc.setFill(toFxColor(colour))
    gc.fill()
  }

  override def tlShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit =
  { segsPath(shape)
    gc.setLineWidth(lineWidth)
    gc.setStroke(toFxColor(colour))
    gc.stroke()
  }

  override def clear(colour: Colour): Unit =
  { gc.setFill(toFxColor(colour))
    gc.fillRect(0, 0, width, height)
  }

  def getTime: Long = System.currentTimeMillis
  import animation._
  override def timeOut(f: () => Unit, millis: Integer): Unit = new Timeline(new KeyFrame(util.Duration.millis(millis.doubleValue()),
    (ae: event.ActionEvent) => f())).play
   
  override protected[this] def tlClip(pts: Polygon): Unit =
  { gc.beginPath
    gc.moveTo(pts.head1, pts.head2)
    pts.foreachPairTail(gc.lineTo)
    gc.closePath()
    gc.clip()
  }

  override def gcSave(): Unit = gc.save()
  override def gcRestore(): Unit = gc.restore()
  def saveFile(fileName: String, output: String): Unit = saveRsonFile(yourDir, fileName, output: String)
  def loadFile(fileName: String): EMon[String] = loadRsonFile(yourDir / fileName)
}