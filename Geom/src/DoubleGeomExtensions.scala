/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import impunits.*

/** Extension methods class for [[Double]], for the geom package. */
class GeomDoubleExtensions(thisDouble: Double)
{ /** Extension methods multiplies this scalar [[Double]] by the operand [[Length]]. If you want a more precise return type such as [[Netres]] or [[Miles]] put
   * the [[Length]] object first. */
  @inline def * (operator: Length): Length = operator * thisDouble

  /** Returns this [[Double]] value in [[Femtometres]]. */
  @inline def femtometres: Femtometres = Femtometres(thisDouble)

  /** Returns this [[Double]] value in [[Picometres]]. */
  @inline def picometres: Picometres = Picometres(thisDouble)

  /** Returns this [[Double]] value in [[Angstroms]]. */
  @inline def angstroms: Angstroms = Angstroms(thisDouble)

  /** Returns this [[Double]] value in [[Nanometres]]. */
  @inline def nanometres: Nanometres = Nanometres(thisDouble)

  /** Returns this [[Double]] value in [[Micrometres]]. */
  @inline def microMetres: Micrometres = Micrometres(thisDouble)

  /** Returns this [[Double]] value in [[Millimetres]]. */
  @inline def millimetres: Millimetres = Millimetres(thisDouble)

  /** Returns this [[Double]] value in [[Metres]]. */
  @inline def metres: Metres = new Metres(thisDouble)

  /** Returns this [[Double]] value in [[Kilometres]]. */
  @inline def kilometres: Kilometres = Kilometres(thisDouble)

  /** Returns this [[Double]] value in [[Megametres]]. */
  @inline def megametres: Megametres = Megametres(thisDouble)

  /** Returns this [[Double]] value in [[Gigametres]]. */
  @inline def gigametres: Gigametres = Gigametres(thisDouble)

  /** Returns this [[Double]] value in [[Pictares]], picometre². */
  @inline def picares: Picares = Picares(thisDouble)

  /** Returns this [[Double]] value in [[Miilrares]], miilimetre². */
  @inline def millrares: Millares = Millares(thisDouble)

  /** Returns this [[Double]] value in [[Metrares]] metre². */
  @inline def metrares: Metrares = Metrares(thisDouble)

  /** Returns this [[Double]] value in [[Hectares]], hectometre. */
  @inline def hectares: Hectares = Hectares(thisDouble)
  
  /** Returns this [[Double]] value in [[Kilares]], kilometre². */
  @inline def kilares: Kilares = Kilares(thisDouble)
  
  /** Returns this [[Double]] value in [[Yards]]. */
  @inline def yards: Yards = new Yards(thisDouble)

  /** Returns this [[Double]] value in [[Miles]]. */
  @inline def miles: Miles = new Miles(thisDouble)

  /** Returns this [[Double]] value of [[MegaMiles]] millions of miles. */
  @inline def megaMiles: MegaMiles = new MegaMiles(thisDouble)

  def radians: Angle = Angle.radians(thisDouble)

  /** Converts this Double into an absolute angle of the given degrees from 0 until 360 degrees. */
  def degs: Angle = Angle(thisDouble)

  /** Degrees rotation anti-clockwise. Converts this Double into an [[AngleVec]] a positive angle of rotation. Can return values greater than 360 degrees and
   * less than -360 degrees. */
  def degsVec: AngleVec = AngleVec(thisDouble)

  /** Degrees rotation clockwise. Converts this Double into an [[AngleVec]] a negative angle of rotation. Can return values greater than 360 degrees and less
   * than -360 degrees. */
  def degsClk: AngleVec = AngleVec(-thisDouble)
}