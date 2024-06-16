/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, java.net.*, java.io.*, java.time.*

object ServRaw extends App
{ deb("Starting")
  val servSock: ServerSocket = new ServerSocket(8080)
  var numConns: Int = 0
  while(true)
  { val sock: Socket = servSock.accept()
    val conn = new ConnSesh(numConns, sock)
    val thread = new Thread(conn)
    thread.start()
    numConns += 1
  }
  servSock.close()
  deb("Finishing")
}

class ConnSesh(val cNum: Int, val sock: Socket) extends Runnable
{
  override def run(): Unit =
  { val readbuf: BufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream()))
    val outPS = new PrintStream(new BufferedOutputStream(sock.getOutputStream))
    var reqNum = 0
    while(true)
    { println(s"Req $cNum, $reqNum start")
      var line: String = null
      val acc = StringBuff()
      var continue = true
      while (continue)
      { line = readbuf.readLine
        if(line == null)
          if (acc.nonEmpty) continue = false
          else Thread.sleep(10)
        else
          if(line == "") continue = false else acc.grow(line)//; println(line) }
      }
      val req = HttpReq(acc)
      val time: ZonedDateTime = java.time.Instant.now().atZone(ZoneId.of("GMT"))
      println(time.getDayOfWeek)
      req match
      { case Good(hrg: HttpReqGet) => hrg.uri match
        { case "/" =>
          { val repStr: String = IndexPage.httpResp("localhost").out
            outPS.print(repStr)
            outPS.flush()
            deb("Sent Response Index page")
          }

          case "/Documentation/documentation.css" =>
          { val repStr: String = CssDocmentation.httpResp("localhost").out
            outPS.print(repStr)
            outPS.flush()
            deb("Response doc css")
          }

          case "/favicon.ico" =>
          { val resp = HttpFound("localhost", HttpConTypeSvg, Favicon1()).out
            outPS.print(resp)
            outPS.flush()
            deb("Response favicon")
          }

          case id => deb(s"Other URI |$id|")
        }
        case _ => deb("Other match")
      }
      reqNum += 1
    }
    readbuf.close
  }
}