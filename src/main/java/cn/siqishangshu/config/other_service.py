# -*- coding:utf-8 -*-
__author__ = 'ehan'


import os

import traceback


def set_other_conf(data):
    southwest_conf_path = "/home/liuchuan/workspace/adl/config/src/cn/mxsic/java/start/config/southwest.conf"
    if os.path.exists(southwest_conf_path):
        rc = set_value(data,southwest_conf_path)
        return rc
    else:
        return False

def set_value(data,file_path):
    try:
        with open(file_path) as file_hand:
            lines = file_hand.readlines()
            lines_num = 0
            find_flag = False
            for line in lines:
                if "encryption" in line:
                    find_flag = True
                    break
                else:
                    lines_num = lines_num + 1

            if find_flag is True:
                lines[lines_num] = "  encryption = " + data["encryption"] + "\n"
                open(file_path, 'w').writelines(lines)
                return True
            else:
                lines_num = 0
                for line in lines:
                    if "dpi" in line:
                        find_flag = True
                        break
                    else:
                        lines_num = lines_num + 1

                if find_flag is True:
                    lines.insert(lines_num+1,"  encryption = " + data["encryption"] + "\n")
                    open(file_path, 'w').writelines(lines)
                    return True
                else:
                    return False

    except Exception:
        logger.error("store file error,file path = %s,date = %s,"%( file_path,data))

def set_encryption_true():
    data = { "encryption" : "true" }
    set_other_conf(data)

def set_encryption_false():
    data = { "encryption" : "false" }
    set_other_conf(data)

if __name__ == "__main__":
    # set_encryption_false()
    # set_encryption_true()
