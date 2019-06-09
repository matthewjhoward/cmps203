
function hasher(keyVal) 
    local s = keyVal
    local a = {}
    for i = 1, string.len(s) do
        table.insert(a, string.sub(s, i, i))
    end

    local sum = 0
    for i,v in ipairs(a) do
        sum = sum + string.byte(v)
    end

    return sum
end

-- DEFINE USER CLASS
local User = {}
User.__index = User

function User.new(firstnm, lastnm, usernm, passwd)
    local selfUser = setmetatable({}, User)
    selfUser.firstname = firstnm
    selfUser.lastname = lastnm
    selfUser.username = usernm
    selfUser.password = passwd
    return selfUser
end

function User.setFirstname(selfUser, firstnm)
    selfUser.firstname = firstnm
end

function User.setLastname(selfUser, lastnm)
    selfUser.lastname = lastnm
end

function User.setUsername(selfUser, usernm)
    selfUser.username = usernm
end

function User.setPassword(selfUser, passwd)
    selfUser.password = passwd
end

function User.getFirstname(selfUser)
    return selfUser.firstname
end

function User.getLastname(selfUser)
    return selfUser.lastname
end

function User.getUsername(selfUser)
    return selfUser.username
end

function User.getPassword(selfUser)
    return selfUser.password
end

-- DEFINE NODE CLASS
local Node = {}
Node.__index = Node

function Node.new(dataVal)
    local selfNode = setmetatable({}, Node)
    selfNode.data = dataVal
    -- selfNode.nextNode = Node.new(nil)
    selfNode.nextNode = nil
    return selfNode
end

function Node.getData(selfNode)
    return selfNode.data
end

-- DEFINE HASH ENTRY CLASS
local HashEntry = {}
HashEntry.__index = HashEntry

function HashEntry.new(keyVal, val)
    local selfHashEntry = setmetatable({}, HashEntry)
    selfHashEntry.key = keyVal
    selfHashEntry.value = val
    -- selfHashEntry.next = HashEntry.new(nil, nil)
    selfHashEntry.next = nil
    return selfHashEntry
end

-- DEFINE LINKED STACKER CLASS

local LinkedStacker = {}
LinkedStacker.__index = LinkedStacker

function LinkedStacker.new()
    local selfLinkedStacker = setmetatable({}, LinkedStacker)
    -- selfLinkedStacker.top = Node.new(nil)
    selfLinkedStacker.top = nil
    return selfLinkedStacker
end

function LinkedStacker.isEmpty(selfLinkedStacker)
    if selfLinkedStacker.top == nil then
        return true
    else
        return false
    end
end

function LinkedStacker.push(selfLinkedStacker, dataVal)
    local newNode = Node.new(dataVal)
    newNode.nextNode = selfLinkedStacker.top
    selfLinkedStacker.top = newNode
    return true
end

function LinkedStacker.pop(selfLinkedStacker)
    if selfLinkedStacker:isEmpty() == true then 
        return false
    end
    
    local nodeToDelete = selfLinkedStacker.top
    selfLinkedStacker.top = selfLinkedStacker.top.nextNode
    nodeToDelete.data = nil
    return nil
end

function LinkedStacker.peek(selfLinkedStacker)
    return selfLinkedStacker.top.data
end

-- DEFINE HASH MAPPER CLASS

local HashMapper = {}
HashMapper.__index = HashMapper

function HashMapper.new()
    local selfHashMapper = setmetatable({}, HashMapper)
    selfHashMapper.size = 0
    selfHashMapper.nBuckets = 20
    selfHashMapper.buckets = {}
    -- selfHashMapper.undoStack = LinkedStacker.new()
    selfHashMapper.undoStack = nil

    for i=0, selfHashMapper.nBuckets do 
        table.insert(selfHashMapper.buckets, nil)
    end

    return selfHashMapper
end

function HashMapper.size(selfHashMapper)
    return selfHashMapper.size
end

function HashMapper.bucketIndex(selfHashMapper, keyVal)
    local hash = hasher(keyVal)
    local idx = hash % selfHashMapper.nBuckets
    return idx
end

function HashMapper.put(selfHashMapper, keyVal, val)
    -- print("Key:" .. keyVal)
    local idx = selfHashMapper:bucketIndex(keyVal)
    -- print(idx)
    local head = selfHashMapper.buckets[idx]

    while head ~= nil do
        if head.key == keyVal then
            head.value = val
            return
        end
        head = head.next
    end

    selfHashMapper.size = selfHashMapper.size + 1
    head = selfHashMapper.buckets[idx]
    local add = HashEntry.new(keyVal, val)
    add.next = head
    selfHashMapper.buckets[idx] = add

    selfHashMapper:resizeTable() 
end

function HashMapper.get(selfHashMapper, keyVal)
    local idx = selfHashMapper:bucketIndex(keyVal)
    local head = selfHashMapper.buckets[idx];
    while head ~= nil do
        if head.key == keyVal then
            return head.value
        end
        head = head.next
    end
    return nil
end

function HashMapper.remove(selfHashMapper, keyVal)
    local idx = selfHashMapper:bucketIndex(keyVal)
    local head = selfHashMapper.buckets[idx]
    local prev = nil
    while head ~= nil do
        if head.key == keyVal then
            break
        end
        prev = head
        head = head.next
    end

    if head == nil then
        return nil
    end
    
    if selfHashMapper.undoStack == nil then             
        selfHashMapper.undoStack = LinkedStacker.new(nil)
    end

    selfHashMapper.undoStack:push(head.value)
    selfHashMapper.size = selfHashMapper.size - 1

    if prev ~= null then
        prev.next = head.next
    else
        selfHashMapper.buckets[idx] = head.next
    end

    return head.value
end

function HashMapper.undoRemove(selfHashMapper)
    if selfHashMapper.undoStack == nil then             
        selfHashMapper.undoStack = LinkedStacker.new(nil)
    end

    if selfHashMapper.undoStack:isEmpty() == true then
        print("There are no removals to undo!")
        return false
    else 
        local user = selfHashMapper.undoStack:peek()
        local username = user:getUsername()
        selfHashMapper:put(username, user)

        print("Removal of user " .. username .. " is undone!")

        selfHashMapper.undoStack:pop()
        return true
    end
end

function HashMapper.resizeTable(selfHashMapper)
    if selfHashMapper.size / selfHashMapper.nBuckets >= 0.6 then
        local tempBuckets = selfHashMapper.buckets
        selfHashMapper.buckets = {}
        selfHashMapper.nBuckets = 2*selfHashMapper.nBuckets
        selfHashMapper.size = 0
        for i=0,selfHashMapper.nBuckets do --given range could be wrong?
            table.insert(selfHashMapper.buckets, nil)
        end
        for i in pairs(tempBuckets) do
            local entry = tempBuckets[i]
            while entry ~= nil do
                selfHashMapper:put(entry.key, entry.value)
                entry = entry.next
            end
        end
    end
end

-- ///////////////////////////////////////////////////////////////////////////////////////////

-- ADD USER CODE
function addUser(hashTable)
    -- print("add")

    local ynAnswer = ""
    local firstnm = ""
    local lastnm = ""
    local usernm = ""
    local passwd = ""

    while true do
        print("What will the First Name be for the User you would like to add?")
        firstnm = io.read()

        print("What will the Last Name be for the User you would like to add?")
        lastnm = io.read()

        print("What will be the Username of the User you would like to add?")
        usernm = io.read()

        -- if hashTable[usernm] ~= nil then
        if hashTable:get(usernm) ~= nil then
            -- while hashTable[usernm] ~= nil do
            while hashTable:get(usernm) ~= nil do
                print("There is already a User with that username in the hash table! Please try another username: ")
                usernm = io.read()
            end
        end

        print("What will the Password be for the User you would like to add?")
        passwd = io.read()

        local user = User.new(firstnm, lastnm, usernm, passwd)
        -- hashTable[usernm] = user 
        hashTable:put(usernm, user)
 
        print("User " .. usernm .. " added to the hash table!")
        print("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
        ynAnswer = io.read()

        if ynAnswer == "n" or ynAnswer == "N" then 
            break
        end
    end
end

-- GET USER CODE
function getUser(hashTable)
    -- print("get")

    local usernm = ""

    print("What is the Username of the User you would like to retrieve?")
    usernm = io.read()

    -- local user = hashTable[usernm]
    local user = hashTable:get(usernm)

    if user == nil then
        print("Could not find User with username " .. usernm .. ".")
    else
        print("User " .. usernm .. " retrieved!")
        return user
    end

    return nil
end

-- REMOVE USER CODE
function removeUser(hashTable)
    local ynAnswer = ""
    local usernm = ""

    while true do
        print("What is the username of the User you would like to remove: ");
        usernm = io.read()

        -- if hashTable[usernm] == nil then
        if hashTable:get(usernm) == nil then
            print("User not found! ")
        else 
            -- hashTable[usernm] = nil
            hashTable:remove(usernm)
            -- if hashTable[usernm] == nil then
            if hashTable:get(usernm) == nil then
                print("User " .. usernm  .. " removed from the hash table!");
            end
        end

        print("Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
        ynAnswer = io.read()

        if ynAnswer == "n" or ynAnswer == "N" then 
            break
        end
    end
end

--PRINT EFFICIENCY CODE
function printEfficiencyData(hshTable)
    print("PrintE")
end


-- PROCESS MENU CODE
function processMenu(hashTable)
    local ynAnswer = ""
    local letterChoice = ""

    while true do
        print("What would you like to do with the User database? Select a letter from the list below")
        print("A. Insert a new User")
        print("B. Find an existing User")
        print("C. Remove an existing User")
        print("D. Undo removal of User")
        print("E. Print efficiency data")
        print("F. Quit program")
        letterChoice = io.read()

        if letterChoice == "a" or letterChoice == "A" then
            addUser(hashTable)
        elseif letterChoice == "b" or letterChoice == "B" then
            local user = getUser(hashTable)
            if user ~= nil then
                print("First Name: " .. user:getFirstname())
                print("Last Name: " .. user:getLastname())
                print("Username: " .. user:getUsername())
                print("Password " .. user:getPassword())
            end
        elseif letterChoice == "c" or letterChoice == "C" then
            removeUser(hashTable)
        elseif letterChoice == "d" or letterChoice == "D" then
            hashTable:undoRemove(hashTable)
        elseif letterChoice == "e" or letterChoice == "E" then
            printEfficiencyData(hashTable)
        elseif letterChoice == "f" or letterChoice == "F" then 
            break
        else
            print("Letter not valid!")
        end 
    end
end

function runTests(hashTable)
    local lines = {}
    for line in io.lines("input.txt") do
        table.insert(lines, line)
    end
    local users = {}
    local add_times = {}
    local del_times = {}
    local undo_times = {}

    for i in pairs(lines) do
        local tokens = {}
        for word in string.gmatch(lines[i], '([^,]+)') do
            table.insert(tokens, word)
        end
        local user = User.new(tokens[1], tokens[2], tokens[3], tokens[4])
        
        table.insert(users, user)
    end

    local i = 0
    local start_time = os.clock()
    for j in pairs(users) do
        user = users[j]
        i = i+1
        if(type(user)~="table")
        then
            hashTable:put(user.username, user)
            
        end
        if (i%10==0) then
            -- print((os.clock()-start_time)*1000)
            table.insert(add_times, (os.clock()-start_time)*1000)
        end

        
    end
    i = 0
    start_times = os.clock()
    for j in pairs(users) do
        user = users[j]
        i = i+1
        if(type(user)~="table")
        then
            hashTable:remove(user.username)
        end
        if (i%10==0) then
            -- print((os.clock()-start_time)*1000)
            table.insert(del_times, (os.clock()-start_time)*1000)
        end
    end

    i = 0
    start_times = os.clock()
    for j in pairs(users) do
        user = users[j]
        -- print(user.username)
        i = i+1
        if(type(user)~="table")
        then
            hashTable:undoRemove()
        end
        if (i%10==0) then
            -- print((os.clock()-start_time)*1000)
            table.insert(undo_times, (os.clock()-start_time)*1000)
        end
    end
    local file = io.open("output.txt", "w")
    io.output(file)
    io.write(table.concat(add_times, ",") .. "\n")
    io.write(table.concat(del_times, ",") .. "\n")
    io.write(table.concat(undo_times, ",") .. "\n")
    io.close(file)
    -- print()
end


-- MAIN CODE
local lines = {}

for line in io.lines("input.txt") do
    table.insert(lines, line)
end

-- local hashTable = {}
local hashTable = HashMapper.new()

for i in pairs(lines) do
    local tokens = {}
    for word in string.gmatch(lines[i], '([^,]+)') do
        table.insert(tokens, word)
    end

    local user = User.new(tokens[1], tokens[2], tokens[3], tokens[4])
    -- hashTable[tokens[3]] = user 
    hashTable:put(tokens[3], user)
end

local doMenu = false
if doMenu then
    processMenu(hashTable)
else    
    runTests(hashTable)
end






