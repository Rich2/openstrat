/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import org.scalajs.dom.*, org.scalajs.dom.html, pweb.*

/** Base trait for JavaScript to updates HTML content due to changes from HTML input or Select elements. */
trait JsUpdater

/** Constructs a JavaScript [[JsUpdater]] for each [[PageHtmlUpdater]]. */
def aggPage(page: PageHtmlUpdater): Unit = page.inpAcc.foreach{ uil =>
  val elem: html.Element = document.getElementById(uil.idStr).asInstanceOf[html.Element]
  if (elem != null){ uil match
    { case upInt: UpdaterIntInput => UpdaterIntJs(upInt, elem)
      case upDbl: UpdaterDblInput => JsUpdaterDbl(upDbl, elem)
      case upStr: UpdaterStr => UpdaterStrJs(upStr, elem)
      case upSel: UpdaterSelect => UpdaterSelectJs(upSel, elem)
    }
  }
}