module Project

type User(firstname, lastname, username, password) =
    member this.firstname = firstname
    member this.lastname = lastname
    member this.username = username
    member this.password = password

type Node(key, value) =
    let mutable theKey = key
    let mutable theValue = value
    let mutable theNext = None
    member this.key
        with get() = theKey
        and set(newKey) = theKey <- newKey
    member this.value
        with get() = theValue
        and set(newVal) = theValue <- newVal
    member this.next
        with get() = theNext
        and set(newNext) = theNext <- newNext

type LinkedStacker() =
    let mutable theTop = None
    member this.top
        with get() = theTop
        and set(newTop) = theTop <- newTop

    member this.isEmpty =
        match this.top with
        | Some(i) -> false
        | None -> true

    member this.push key value =
        let mutable returnVal = true
        let mutable newNode = Node(key, value)
        newNode.next <- this.top
        this.top <- Some(newNode)
        returnVal
    member this.pop =
        if this.isEmpty
        then
            false
        else
            let mutable toDelete = this.top
            match this.top with
            | Some(i) -> 
                this.top <- i.next
                toDelete <- None
                true
            | None -> 
                printfn "SHouldn't happen"
                true
    member this.peek =
        this.top


type HashEntry(key, value) = 
    let mutable theValue = value
    let mutable theNext = None
    member this.key = key
    member this.value 
        with get() = theValue
        and set(newVal) = theValue <- newVal
    
    member this.next
        with get() = theNext
        and set(newNext) = theNext <- newNext

type HashMapper() = 
    let mutable theSize = 0
    let mutable theBuckets : Option<HashEntry> array = Array.zeroCreate 20
    let mutable theNBuckets = 20
    let mutable theUndoStack = LinkedStacker()

    member this.undoStack
        with get() = theUndoStack
        and set(newStack) = theUndoStack <- newStack

    member this.buckets
        with get() = theBuckets
        and set(newBuckets) = theBuckets <- newBuckets
    member this.updateBucket idx value = Array.set this.buckets idx value

    member this.nBuckets
        with get() = theNBuckets
        and set(newN) = theNBuckets <- newN
    
    member this.size
        with get() = theSize
        and set(newSize) = theSize <- newSize

    member this.getSize = this.size

    member this.bucketIndex key = 
        let hash = (hash key) &&& 0x7fffffff
        let idx = hash % this.nBuckets
        idx

    member this.resizeTable =
        let sizeCheck = (float this.size)/(float this.nBuckets)
        if sizeCheck >= 0.6
        then
            printfn "Resizing"
            let mutable tempBuckets = Array.copy this.buckets
            let mutable keepGoing = true
            
            this.nBuckets <- (this.nBuckets + this.nBuckets)
            this.buckets <- Array.zeroCreate this.nBuckets
            this.size <- 0

            for i in 0 .. ((Array.length tempBuckets)-1) do
                let mutable entry = tempBuckets.[i]
                while keepGoing do
                    entry <- tempBuckets.[i]
                    match entry with
                    | Some(x) ->
                        this.put x.key x.value
                        tempBuckets.[i] <- x.next
                    | None -> 
                        keepGoing <- false


    member this.put key value =
        let idx = this.bucketIndex(key)
        let mutable head = this.buckets.[idx]
        let mutable keepGoing = true
        let mutable setValue = false

        while keepGoing do
            match head with
            | Some(i) -> 
                if i.key = key 
                then 
                    i.value <- value 
                    setValue <- true
                    keepGoing <- false
                else
                    head <- i.next
                    
            | None -> keepGoing <- false
        
        if not setValue
        then
            this.size <- this.size + 1
            // let newBuckets = Array.copy this.buckets
            head <- this.buckets.[idx]
            let add = new HashEntry(key, value)
            add.next <- head
            this.buckets.[idx] <- (Some(add))
            // this.buckets <- newBuckets
            // this.resizeTable

    member this.get (key:string) =
        let idx = this.bucketIndex(key)
        let mutable head = this.buckets.[idx]
        let mutable keepGoing = true
        let mutable returnVal = None

        while keepGoing do
            match head with
            | Some(i) ->
                if i.key = key
                then
                    returnVal <- Some(i.value)
                    keepGoing <- false
                else
                    head <- i.next
            | None -> keepGoing <- false

        returnVal
    
    member this.remove key =
        let idx = this.bucketIndex(key)
        let mutable head = this.buckets.[idx]
        let mutable prev = None
        let mutable keepGoing = true
        let mutable returnVal = None
        while keepGoing do
            match head with
            | Some(i) -> 
                if i.key = key
                then
                    keepGoing <- false
                else
                    prev <- head
                    head <- i.next
            | None -> keepGoing <- false
        
        match head with
        | Some(i) -> 
            this.undoStack.push i.key i.value
            match prev with
            | Some(x) -> 
                x.next <- i.next
                returnVal <- Some(i.value)
            | None -> 
                Array.set this.buckets idx (i.next)
                returnVal <- Some(i.value)
        | None -> returnVal <- None
        
        returnVal
    
    member this.undoRemove = 
        if this.undoStack.isEmpty
        then
            printfn "There are no removals to undo!"
            false
        else
            let mutable user = this.undoStack.peek
            match user with
            | Some(i) -> 
                this.put i.key i.value
                // printfn "Removal of user %s is undone!" i.key
                this.undoStack.pop
            | None -> 
                // printfn "Shouldn't happen"
                true
            
           
open System

let addUser (table:HashMapper) =
    let mutable ynAnswer = "y"
    let mutable firstname = ""
    let mutable lastname = ""
    let mutable username : string = "user2"
    let mutable password = ""
    while (ynAnswer <> "n") do
        printfn "What will the first name be for the User you would like to add: "
        firstname <- Console.ReadLine()
        printfn "What will the last name be for the User you would like to add: "
        lastname <- Console.ReadLine()
        printfn "What will the username be for the User you would like to add: "
        username <- Console.ReadLine()
        
        let mutable user = table.get username
        match user with
        | Some(i) ->
            let mutable keepGoing = true
            while keepGoing do
                user <- table.get username
                match user with
                | Some(i) -> 
                    printfn "There is already a User with that username in the hash table! Please try another username: "
                    username <- Console.ReadLine()
                | None -> keepGoing <- false
        | None -> printfn "Adding user..."

        printfn "What will the password be for the User you would like to add: "
        password <- Console.ReadLine()
        let mutable newUser = User(firstname, lastname, username, password)
        table.put username newUser
        printfn "User %s added to the hash table!" username
        printfn "Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): "
        ynAnswer <- Console.ReadLine()


let removeUser (table:HashMapper) = 
    let mutable ynAnswer = "y"
    let mutable username = ""
    while (ynAnswer <> "n") do
        printfn "What is the username of the User you would like to remove: "
        username <- Console.ReadLine()
        let mutable user = table.get username
        match user with
        | Some(i) -> 
            table.remove username
            user <- table.get username
            match user with
            | None -> printfn "User %s removed from the hash table!" username
            | Some(i) -> printfn "Shouldn't happen"
        | None -> printfn "User not found!"

        printfn "Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): "
        ynAnswer <- Console.ReadLine()

let getUser (table:HashMapper) =
    let mutable username = ""
    printfn "What is the username of the User you would like to retrieve?: "
    username <- Console.ReadLine()
    let mutable user = table.get username
    match user with
    | Some(i) -> 
        printfn "User (%s) retrieved!" username
        user
    | None -> 
        printfn "Could not find User with username: %s" username
        None


let processMenu table = 
    let mutable letter:String = ""
    while (letter <> "f") do
      printfn "What would you like to do with the User database? Select a letter from the list below"
      printfn "A. Insert a new User"
      printfn "B. Find an existing User"
      printfn "C. Remove an existing User"
      printfn "D. Undo removal of User"
      printfn "E. Print efficiency data"
      printfn "F. Quit program"
      printfn "Please input your selected letter here, then press Enter: "
      letter <- Console.ReadLine()
      
     
      
      match letter with
      | "a" -> addUser table
      | "b" ->
        let mutable user = getUser table
        match user with
        | Some(i) -> 
            printfn "First Name: %s" i.firstname
            printfn "Last Name: %s" i.lastname
            printfn "Username: %s" i.username
            printfn "Password: %s" i.password
        | None -> printfn "No User..."
      | "c" -> removeUser table
      | "d" -> 
        let bool = table.undoRemove
        printfn ""
      | "e" -> printfn "Nothing here..."
      | "f" -> printfn "Quitting..."
      | _ -> printfn "Input not valid!"

let runTests (table:HashMapper) =
    let lines = System.IO.File.ReadLines("input.txt")
    let mutable users = List.empty<User>
    let mutable add_times = List.empty<float>
    let mutable del_times = List.empty<float>
    let mutable undo_times = List.empty<float>

    for line in lines do
        let tokens = line.Split [|','|]
        let user = User(tokens.[0], tokens.[1], tokens.[2], tokens.[3])
        users <- users @ [user]

    printfn "Adds"
    let mutable i = 0
    let mutable start_time = DateTime.Now.Ticks
    for user in users do
        i <- i+1
        
        // printfn "%s" user.username
        table.put user.username user
        if i%10 = 0
        then
            add_times <- add_times @ [(float (DateTime.Now.Ticks - start_time))/(100000.0)]
    printfn "%i" table.size
    printfn "Dels"
    i <- 0
    start_time <- DateTime.Now.Ticks
    for user in users do
        i <- i+1
        let bool = 
            match (table.remove user.username) with
            | Some(i) -> true
            | None -> false
        if (not bool)
        then printfn "%s" user.username
        if i%10 = 0
        then
            del_times <- del_times @ [(float (DateTime.Now.Ticks - start_time))/(100000.0)]

    printfn "Undos"
    i <- 0
    let mutable count = 0
    start_time <- DateTime.Now.Ticks
    for user in users do
        i <- i+1
        let bool = table.undoRemove
        if (not bool)
        then
            // printfn "%s" user.username
            count <- count + 1
        if i%10 = 0
        then
            undo_times <- undo_times @ [(float (DateTime.Now.Ticks - start_time))/(100000.0)]
    let convertToString l = l |> List.map (sprintf "%A") |> String.concat ","
    let delString = convertToString del_times
    let undoString = convertToString undo_times
    let addString = convertToString add_times
    let sw = System.IO.StreamWriter("output.txt")
    sw.WriteLine(addString)
    sw.WriteLine(delString)
    sw.WriteLine(undoString)
    // printfn "%s" addString
    // printfn "%s" delString
    // printfn "%s" undoString
    sw.Close()
 
[<EntryPoint>]
let main args =
    let doMenu = true
    let map = HashMapper()
    if doMenu
    then
        let filename = "input.txt"
        let lines = System.IO.File.ReadLines(filename)
        for line in lines do
            let tokens = line.Split [|','|]
            let firstname = tokens.[0]
            let lastname = tokens.[1]
            let username = tokens.[2]
            let password = tokens.[3]
            let user = User(firstname, lastname, username, password)
            map.put username user

        processMenu map
    else
        runTests map
    0