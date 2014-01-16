
def genTest
  numMachines = 1 + rand(32)
  numJobs = 1 + rand(32)
  puts "#{numMachines} #{numJobs}"
  changeTimes = (0...numMachines).map { rand(32) }
  puts changeTimes.join(" ")
  (0...numJobs).each do
    numTasks = 1 + rand(32)
    puts numTasks
    pairs = (0...numTasks).map do
      machine = 1 + rand(numMachines)
      time = 1 + rand(32)
      [machine, time]
    end
    puts pairs.join(" ")
  end
end

genTest