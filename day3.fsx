///PART 1

let rec findLargestJolts (leading: int) (trailing: int) (position: int) (current: string) : int =
    let currentValue = leading * 10 + trailing
    if position < 0
    then currentValue
    else
        let nextDigit = Int32.Parse(current.Substring(position, 1));
        let valueAfterFirstCheck =
            if nextDigit * 10 + leading > currentValue
            then nextDigit * 10 + leading
            else currentValue
        let finalValue = 
            if nextDigit * 10 + trailing > valueAfterFirstCheck
            then nextDigit * 10 + trailing
            else valueAfterFirstCheck
        findLargestJolts (finalValue / 10) (finalValue % 10) (position - 1) current



getInput() |>
    List.fold (fun sum line -> 
        let length = line.Length
        sum + findLargestJolts  (Int32.Parse(line.Substring(length - 2, 1))) (Int32.Parse(line.Substring(length - 1, 1))) (line.Length - 3) line
    ) 0

////PART 2

let rec moveCurrentDigitUp (currentValue: string) (position: int) (digit: int) : string = 
    if position > currentValue.Length - 1
    then currentValue
    else 
        let digitUnderCheck = Int32.Parse(currentValue.Substring(position, 1))
        if digit < digitUnderCheck
        then currentValue
        else 
            let newValue = currentValue.Remove(position, 1).Insert(position, Convert.ToString(digit))
            moveCurrentDigitUp newValue (position + 1) digitUnderCheck

let rec findLargest12Jolts (currentValue: string) (position: int) (line: string) : Int128 =
    if position < 0 
    then (Int128.Parse(currentValue))
    else 
        let nextDigit = Int32.Parse(line.Substring(position, 1));
        let newValue = moveCurrentDigitUp currentValue 0 nextDigit
        findLargest12Jolts newValue (position - 1) line
        

getInput() |>
    List.fold (fun sum line -> 
        let length = line.Length
        sum + findLargest12Jolts (line.Substring(length - 12, 12)) (line.Length - 13) line
    ) (Int128.Parse("0"))
