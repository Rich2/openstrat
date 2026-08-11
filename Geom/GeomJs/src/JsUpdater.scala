/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
trait JsUpdater

object JsUpdater
{ /** Factory apply method, constructs the appropriate [[JsUpdater]] for the given [[UpdaterInputLike]]. */
  def apply(inputer: UpdaterInputLike): JsUpdater = inputer match
  { case uii: UpdaterIntInput => JsUpdaterInt(uii)
    case udi: UpdaterDblInput => JsUpdaterDbl(udi)
    case iut: UpdaterStr => JsUpdaterStr(iut)
    case iua: UpdaterSelect => UpdaterSelectJs(iua)
  }
}