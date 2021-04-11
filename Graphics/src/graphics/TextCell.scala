/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A text cell is text in box, whose scaling is determined at display time. It will normally be displayed in some kind of command bar or table
 * structure. */
trait TextCell
{ def textStr: String
}

object TextCell
{
  /*implicit val slateImplicit: Slate[TextCell] = (obj: TextCell, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[TextCell] = (obj: TextCell, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[TextCell] = (obj: TextCell, radians: Double) => obj.rotateRadians(radians)
  
  implicit val mirrorAxisImplicit: ReflectAxisOffset[TextCell] = new ReflectAxisOffset[TextCell] {
    /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: TextCell, yOffset: Double): TextCell = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: TextCell, xOffset: Double): TextCell = obj.reflectYParallel(xOffset)
  }
  implicit val prolignImplicit: Prolign[TextCell] = (obj, matrix) => obj.prolign(matrix)*/
}

trait TextRect extends TextCell

/** Implementation class for [[TextCell]]. */
case class TextBox(textStr: String) extends TextCell

/** A graphical control, whose scaling is determined at display time. It will  that will normally be displayed in some kind of command bar or table
 * structure. */
case class TextCtrl(textStr: String, action: MouseButton => Unit) extends TextCell
{

}