package me.calebbassham.scenariomanager.api.exceptions

class MultipleTeamAssigningScenariosEnabledException : RuntimeException("There are multiple team assigning scenarios enabled. Only one team assigning scenario an be used at a time.")