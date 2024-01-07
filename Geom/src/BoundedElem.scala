/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2D geometric element, that has a defined bounding rectangle, [[BoundingRect]]. This trait is for layout, such as placing Graphic elements in
 *  rows and columns. It includes polygon and shape graphics but not line and curve graphics. */
trait BoundedElem extends Any with GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: Rect

  /** The width of the [[BoundingRect]] of this object. */
  def boundingWidth: Double = boundingRect.width

  /** The height of the [[BoundingRect]] of this object. */
  def boundingHeight: Double = boundingRect.height

  def boundTopRight: Pt2 = boundingRect.topRight
  def brBounding: Pt2 = boundingRect.bottomRight
  def tlBounding: Pt2 = boundingRect.topLeft
  def blBounding: Pt2 = boundingRect.bottomLeft

  /** The centre of the bounding rectangle. consider also using cenDefault. */
  @inline final def boundCen: Pt2 = boundingRect.cen

  /** If the geometric element has a defined centre then the cenDefault uses that, else it defaults to the centre of the bounding rectangle. */
  def cenDefault: Pt2 = boundingRect.cen
}

/** This will be deprecated and its methods transfered to [[BoundingExtensions]]. */
class BoundedExtensions[T <: BoundedElem](val thisT: T) extends AnyVal
{ /** 2D geometric translation transformation on this type T, returning an object of type T with its default centre at the parameter point. */
  def cenDefaultTo(newCenDefault: Pt2)(implicit ev: Slate[T]): T = ev.slateT(thisT, thisT.cenDefault >> newCenDefault)
}

/** Type class for the production of bounding rectangles. */
trait Bounding[A]
{ /** Extension method for the bounding rectangle of this object. */
  def bounds(obj: A): Rect

  /** The centre of the bounding rectangle. consider also using cenDefault. */
  @inline final def boundCen(obj: A): Pt2 = bounds(obj).cen

  /** The width of the bounding rectangle. of this object. */
  def boundingWidth(obj: A): Double = bounds(obj).width

  /** Top right of the bounding rectangle. */
  def boundsTR(obj: A): Pt2 = bounds(obj).topRight

  /** Bottom right of the bounding rectangle. */
  def boundsBR(obj: A): Pt2 = bounds(obj).bottomRight

  /** Top left of the bounding rectangle. */
  def boundsTL(obj: A): Pt2 = bounds(obj).topLeft

  /** bottom left of the bounding rectangle. */
  def boundsBL(obj: A): Pt2 = bounds(obj).bottomLeft
}

object Bounding
{ /** Implicit [[Bounding]] type class instances evidence for objects that extends [[BoundedElem]]. */
  implicit def boundedEv[A <: BoundedElem]: Bounding[A] = obj => obj.boundingRect

  /** Implicit [[Bounding]] type class instances evidence for [[Sequ]]. */
  implicit def sequEv[A](implicit evA: Bounding[A]): Bounding[Sequ[A]] = new Bounding[Sequ[A]]
  { override def bounds(obj: Sequ[A]): Rect = if (obj.length == 0) NoBounds else obj.foldLeft{ (acc, el) => acc || evA.bounds(el) }
  }
}

class BoundingExtensions[A](val thisObj: A, evA: Bounding[A])
{
  def bounds: Rect = evA.bounds(thisObj)

  /** 2D geometric translation transformation on the type A, returning an object of type A with the centre of its bounding rectangle at the parameter
   * point. */
  def boundsCenTo(newCen: Pt2)(implicit ev: Slate[A]): A = ev.slateT(thisObj, evA.boundCen(thisObj) >> newCen)

  /** 2D geometric translation transformation on this type A, returning an object of type A with the top right of its bounding rectangle at the
   *  parameter point. */
  def boundsTRTo(newTopRight: Pt2)(implicit ev: Slate[A]): A = ev.slateT(thisObj, evA.boundsTR(thisObj) >> newTopRight)

  /** 2D geometric translation transformation on this type A, returning an object of type A with the bottom right of its bounding rectangle at the
   *  parameter point. */
  def boundsBRTo(newBottomRight: Pt2)(implicit ev: Slate[A]): A = ev.slateT(thisObj, evA.boundsBR(thisObj) >> newBottomRight)

  /** 2D geometric translation transformation on this type A, returning an object of type A with the bottom left of its bounding rectangle at the
   *  parameter point. */
  def boundsBLTo(newBottomLeft: Pt2)(implicit ev: Slate[A]): A = ev.slateT(thisObj, evA.boundsBL(thisObj) >> newBottomLeft)

  /** 2D geometric translation transformation on this type A, returning an object of type A with the top left of its bounding rectangle at the
   * parameter point. */
  def boundsTLTo(newTopLeft: Pt2)(implicit ev: Slate[A]): A = ev.slateT(thisObj, evA.boundsTL(thisObj) >> newTopLeft)
}