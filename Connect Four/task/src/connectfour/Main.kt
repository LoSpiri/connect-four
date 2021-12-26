package connectfour

    fun match (rows:Int, columns:Int, firstPlayer:String, secondPlayer:String, matchNumber:Int = -1):Int {

        if(matchNumber != -1) {
            println("Game #$matchNumber")
        }

        var turn = matchNumber + 1
        val arr = Array(columns+1) { IntArray(rows+1) {0} }

        board(rows,columns,arr)

        do{
            if(turn - matchNumber - 1 >= rows*columns){
                if(matchNumber == -1){
                    println("It is a draw\nGame over!")
                }
                else {
                    return 0
                }
                break
            }
            if(turn%2==0) {println("$firstPlayer's turn:")} // even == turn 1
            else {println("$secondPlayer's turn:")}
            val n = readLine()!!
            if(n!="end") {
                if(!n.all {it.isDigit()}) {
                    println("Incorrect column number")
                }
                else if(n.toInt()>columns || n.toInt()<1) {
                    println("The column number is out of range (1 - $columns)")
                }
                else if(arr[n.toInt() - 1][0] == rows) {
                    println("Column $n is full")
                }
                else{
                    if (turn % 2 == 0) {
                        arr[n.toInt() - 1][0]++
                        arr[n.toInt() - 1][arr[n.toInt() - 1][0]] = 1
                        if(win(rows,columns,arr,1)){
                            board(rows, columns, arr)
                            if(matchNumber == -1){
                                println("Player $firstPlayer won\nGame over!")
                            }
                            else{
                                return 1
                            }
                            break
                        }
                    }
                    else {
                        arr[n.toInt() - 1][0]++
                        arr[n.toInt() - 1][arr[n.toInt() - 1][0]] = 2
                        if(win(rows,columns,arr,2)){
                            board(rows, columns, arr)
                            if(matchNumber == -1){
                                println("Player $secondPlayer won\nGame over!")
                            }
                            else {
                                return 2
                            }
                            break
                        }
                    }
                    board(rows, columns, arr)
                    turn++
                }
            }
            else{
                println("Game over!")
            }
        }while(n != "end")
        return 0
    }


    fun board (rows:Int, columns:Int, arr: Array<IntArray>) {
        for(i in 0 until rows+2){
            for(j in 0..columns) {
                if(i==0){
                    if(j == columns){continue}
                    print(" ${j+1}")
                }
                else if(i==rows+1){
                    when (j) {
                        0 -> print("╚═")
                        columns -> print("╝")
                        else -> print("╩═")
                    }
                }
                else{
                    if(arr[j][rows-i+1]==1) print("║o")
                    else if(arr[j][rows-i+1]==2) print("║*")
                    else print("║ ")
                }
            }
            print("\n")
        }
    }


    fun win (rows:Int, columns:Int, arr: Array<IntArray>, turn: Int):Boolean {
        // vertical win
        for(column in 0 until columns){
            for(row in 1 .. rows-3){
                if(arr[column][row] == turn && arr[column][row+1] == turn && arr[column][row+2] == turn && arr[column][row+3] == turn){
                    return true
                }
            }
        }
        //horizontal win
        for(row in 1 .. rows){
            for(column in 0 until columns-3){
                if(arr[column][row] == turn && arr[column+1][row] == turn && arr[column+2][row] == turn && arr[column+3][row] == turn){
                    return true
                }
            }
        }
        //descending diagonal win
        for(row in 4 .. rows){
            for(column in 0 until columns-3){
                if(arr[column][row] == turn && arr[column+1][row-1] == turn && arr[column+2][row-2] == turn && arr[column+3][row-3] == turn){
                    return true
                }
            }
        }
        //ascending diagonal win
        for(row in 1 .. rows-3){
            for(column in 0 until columns-3){
                if(arr[column][row] == turn && arr[column+1][row+1] == turn && arr[column+2][row+2] == turn && arr[column+3][row+3] == turn){
                    return true
                }
            }
        }
        return false
    }


    fun games (): Int {
        println("Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter\n" +
                "Input a number of games:")

        val requested = readLine()!!
        val req = requested.toIntOrNull()
        return when {
            requested == "" -> 1
            req != null && req > 0 -> req
            else -> {
                println("Invalid input")
                games()
            }
        }
    }


    fun main() {

        //SETTING OF THE NAMES AND BOARD DIMENSIONS

        println("Connect Four")
        println("First player's name:")
        val firstPlayer = readLine()!!
        println("Second player's name:")
        val secondPlayer = readLine()!!
        var rows = 0
        var columns = 0
        while(true) {
            val boardSetting = "Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7)"
            println(boardSetting)
            val input = readLine()!!.filter { !it.isWhitespace() }
            if (input == "") {
                rows = 6
                columns = 7
            } else {
                val regex = Regex("^[0-9]+(x|X)[0-9]+\$")
                if (!input.matches(regex)) {
                    println("Invalid Input")
                    continue
                } else {
                    rows = input.first().code - 48
                    if (rows < 5 || rows > 9) {
                        println("Board rows should be from 5 to 9")
                        continue
                    }
                    columns = input.last().code - 48
                    if (columns < 5 || columns > 9) {
                        println("Board columns should be from 5 to 9")
                        continue
                    }
                }
            }
            break
        }

        val games = games()

        if(games == 1){
            println("$firstPlayer VS $secondPlayer\n" +
                    "$rows X $columns board\n" +
                    "Single Game")
            match(rows,columns,firstPlayer,secondPlayer)
        }
        else {
            println("$firstPlayer VS $secondPlayer\n" +
                    "$rows X $columns board\n" +
                    "Total $games games")

            var firstPlayerWins = 0
            var secondPlayerWins = 0
            for(cont in 1..games){
                when (match(rows,columns,firstPlayer,secondPlayer,cont)){
                    1 -> {
                        firstPlayerWins += 2
                        println("Player $firstPlayer won\n" +
                                "Score\n" +
                                "$firstPlayer: $firstPlayerWins $secondPlayer: $secondPlayerWins")
                    }
                    2 -> {
                        secondPlayerWins += 2
                        println("Player $secondPlayer won\n" +
                                "Score\n" +
                                "$firstPlayer: $firstPlayerWins $secondPlayer: $secondPlayerWins")
                    }
                    0 -> {
                        firstPlayerWins++
                        secondPlayerWins++
                        println("It is a draw\n" +
                                "Score\n" +
                                "$firstPlayer: $firstPlayerWins $secondPlayer: $secondPlayerWins")
                    }
                }
            }
            println("Game over!")
        }
    }
